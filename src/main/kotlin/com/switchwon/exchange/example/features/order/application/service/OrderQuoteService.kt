package com.switchwon.exchange.example.features.order.application.service

import com.switchwon.exchange.example.features.exchangerate.application.service.ExchangeRateService
import com.switchwon.exchange.example.features.order.application.dto.OrderQuoteRequest
import com.switchwon.exchange.example.features.order.application.dto.OrderQuoteResponse
import com.switchwon.exchange.example.shared.domain.Money
import org.springframework.stereotype.Service

@Service
class OrderQuoteService(
    private val exchangeRateService: ExchangeRateService,
) {

    /**
     * 주문 견적을 조회합니다.
     * - 외화 매수인 경우, 해당 외화를 매수하기 위해 필요한 KRW 금액을 반환합니다.
     * - 외화 매도인 경우, 해당 외화를 매도했을 때 받을 수 있는 KRW 금액을 반환합니다.
     */
    fun getQuote(request: OrderQuoteRequest): OrderQuoteResponse {
        val isBuy = request.fromCurrency.isKrw()
        val forexCurrency = if (isBuy) request.toCurrency else request.fromCurrency
        val forexAmount = Money(currency = forexCurrency, amount = request.forexAmount)

        // 해당 통화의 최신 환율 조회
        val latestExchangeRate = exchangeRateService.findLatestExchangeRate(forexCurrency)

        // 원화로 환산
        val krwAmount = latestExchangeRate.convertForexToKrw(forexAmount = forexAmount).amount
        return OrderQuoteResponse(
            krwAmount = krwAmount,
            appliedRate = latestExchangeRate.rate,
        )
    }

}
