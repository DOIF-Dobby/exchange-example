package com.switchwon.exchange.example.features.order.application.service

import com.switchwon.exchange.example.features.exchangerate.application.service.ExchangeRateService
import com.switchwon.exchange.example.features.exchangerate.domain.ExchangeRate
import com.switchwon.exchange.example.features.exchangerate.domain.ExchangeRateId
import com.switchwon.exchange.example.features.member.domain.MemberId
import com.switchwon.exchange.example.features.order.application.dto.OrderRequest
import com.switchwon.exchange.example.features.order.domain.Order
import com.switchwon.exchange.example.features.order.domain.OrderRepository
import com.switchwon.exchange.example.features.wallet.application.service.WalletService
import com.switchwon.exchange.example.shared.domain.Currency
import com.switchwon.exchange.example.shared.domain.Money
import com.switchwon.exchange.example.system.web.exception.BadRequestException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val exchangeRateService: ExchangeRateService,
    private val walletService: WalletService,
    private val orderValidator: OrderValidator,
) {

    /**
     * 외화 매수/매도 주문을 처리합니다.
     * - 매수인 경우: KRW -> 외화
     * - 매도인 경우: 외화 -> KRW
     */
    @Transactional
    fun order(memberId: MemberId, request: OrderRequest) {
        orderValidator.validateCurrencyPair(
            fromCurrency = request.fromCurrency,
            toCurrency = request.toCurrency,
        )

        // 외화 매수 or 매도 확인
        val isBuy = request.fromCurrency.isKrw()
        val forexCurrency = if (isBuy) request.toCurrency else request.fromCurrency

        // 외화 주문 금액 유효성 검사
        forexCurrency.validateOrderAmountScale(amount = request.forexAmount)

        // 외호 주문 금액이 통화별 최소 주문 금액 보다 큰지 확인
        forexCurrency.validateOrderAmountMin(amount = request.forexAmount)

        // 해당 통화의 최신 환율 조회
        val latestExchangeRate = exchangeRateService.findLatestExchangeRate(forexCurrency)

        // 요청한 환율 ID와 최신 환율 ID 비교
        validateExchangeRate(requestId = request.exchangeRateId, latestId = latestExchangeRate.id)

        // 외화 매수인 경우
        if (isBuy) {
            executeBuyOrder(
                memberId = memberId,
                request = request,
                rate = latestExchangeRate,
            )
        } else {
            executeSellOrder(
                memberId = memberId,
                request = request,
                rate = latestExchangeRate,
            )
        }
    }

    /**
     * 요청한 환율 ID와 최신 환율 ID를 비교하여 일치하지 않으면 예외를 발생시킵니다.
     */
    private fun validateExchangeRate(requestId: ExchangeRateId, latestId: ExchangeRateId) {
        if (requestId != latestId) {
            throw BadRequestException(
                code = "EXCHANGE_RATE_MISMATCH",
                message = "요청한 환율 ID와 최신 환율 ID가 일치하지 않습니다. 요청: ${requestId.value}, 최신: ${latestId.value}",
            )
        }
    }

    /**
     * 외화 매수 주문을 실행합니다. (KRW -> Forex)
     */
    private fun executeBuyOrder(
        memberId: MemberId,
        request: OrderRequest,
        rate: ExchangeRate,
    ) {
        // 외화 매수할 금액
        val forexAmount = Money(currency = request.toCurrency, amount = request.forexAmount)
        // 필요한 KRW 금액 계산
        val requiredKrwAmount = rate.convertForexToKrw(forexAmount = forexAmount)

        walletService.executeExchange(
            memberId = memberId,
            withdrawMoney = Money(currency = Currency.KRW, amount = requiredKrwAmount.amount),
            depositMoney = forexAmount,
        )

        saveOrder(
            memberId = memberId,
            fromMoney = requiredKrwAmount,
            toMoney = forexAmount,
            appliedRate = rate,
        )
    }

    /**
     * 외화 매도 주문을 실행합니다. (Forex -> KRW)
     */
    private fun executeSellOrder(
        memberId: MemberId,
        request: OrderRequest,
        rate: ExchangeRate,
    ) {
        // 외화 매도할 금액
        val forexAmount = Money(currency = request.fromCurrency, amount = request.forexAmount)
        // 받게 될 KRW 금액 계산
        val receivedKrwAmount = rate.convertForexToKrw(forexAmount = forexAmount)

        walletService.executeExchange(
            memberId = memberId,
            withdrawMoney = forexAmount,
            depositMoney = receivedKrwAmount,
        )

        saveOrder(
            memberId = memberId,
            fromMoney = forexAmount,
            toMoney = receivedKrwAmount,
            appliedRate = rate,
        )
    }

    /**
     * Order 엔티티 저장
     */
    private fun saveOrder(
        memberId: MemberId,
        fromMoney: Money,
        toMoney: Money,
        appliedRate: ExchangeRate,
    ) {
        val order = Order(
            memberId = memberId,
            fromMoney = fromMoney,
            toMoney = toMoney,
            appliedRate = appliedRate.rate,
        )

        orderRepository.save(order)
    }
}
