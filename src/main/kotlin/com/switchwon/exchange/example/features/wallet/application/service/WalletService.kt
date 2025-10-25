package com.switchwon.exchange.example.features.wallet.application.service

import com.switchwon.exchange.example.features.exchangerate.application.service.ExchangeRateService
import com.switchwon.exchange.example.features.member.application.service.MemberService
import com.switchwon.exchange.example.features.member.domain.MemberId
import com.switchwon.exchange.example.features.wallet.application.dto.WalletResponse
import com.switchwon.exchange.example.features.wallet.application.dto.WalletSummaryResponse
import com.switchwon.exchange.example.features.wallet.domain.Wallet
import com.switchwon.exchange.example.features.wallet.domain.WalletRepository
import com.switchwon.exchange.example.shared.domain.Currency
import com.switchwon.exchange.example.shared.domain.Money
import com.switchwon.exchange.example.system.web.exception.BadRequestException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class WalletService(
    private val walletRepository: WalletRepository,
    private val memberService: MemberService,
    private val exchangeRateService: ExchangeRateService,
) {

    private val throwWalletNotFoundBlock = { currency: Currency ->
        throw BadRequestException(
            code = "WALLET_NOT_FOUND",
            message = "해당 통화의 지갑을 찾을 수 없습니다. 통화: $currency",
        )
    }

    /**
     * 회원의 지갑 요약 정보를 조회합니다.
     * - 회원 ID로 지갑을 조회하고, 각 통화의 잔액을 KRW로 환산하여 총 잔액을 계산합니다.
     */
    @Transactional(readOnly = true)
    fun findWalletSummary(memberId: MemberId): WalletSummaryResponse {
        val member = memberService.findMember(memberId)
        val wallets = walletRepository.findByMember(member)
        val rateMap = exchangeRateService.findLatestExchangeRateMap()

        val totalKrwBalance = wallets.sumOf { wallet ->
            when {
                wallet.currency.isKrw() -> wallet.balanceAmount
                else -> rateMap[wallet.currency]
                    ?.convertForexToKrw(wallet.balance)
                    ?.amount
                    ?: BigDecimal.ZERO
            }
        }

        return WalletSummaryResponse(
            totalKrwBalance = totalKrwBalance,
            wallets = wallets.map { WalletResponse.from(it) },
        )
    }

    /**
     * 회원의 지갑을 조회합니다.
     * - 회원 ID로 지갑을 조회합니다.
     */
    @Transactional(readOnly = true)
    fun findWalletsByMemberId(memberId: MemberId): List<Wallet> {
        val member = memberService.findMember(memberId)
        return walletRepository.findByMember(member)
    }

    /**
     * 회원의 지갑에서 통화 교환을 수행합니다.
     * - 출금할 통화와 금액, 입금할 통화와 금액을 입력받아
     *   해당 통화의 지갑에서 출금하고,
     *   다른 통화의 지갑에 입금합니다.
     */
    @Transactional
    fun executeExchange(
        memberId: MemberId,
        withdrawMoney: Money,
        depositMoney: Money,
    ) {
        val wallets = findWalletsByMemberId(memberId = memberId)
        val fromWallet = wallets.find { it.currency == withdrawMoney.currency } ?: throwWalletNotFoundBlock(withdrawMoney.currency)
        val toWallet = wallets.find { it.currency == depositMoney.currency } ?: throwWalletNotFoundBlock(depositMoney.currency)

        fromWallet.withdraw(withdrawMoney)
        toWallet.deposit(depositMoney)
    }
}
