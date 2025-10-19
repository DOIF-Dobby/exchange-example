package com.switchwon.exchange.example.features.wallet.application.service

import com.switchwon.exchange.example.features.member.domain.Member
import com.switchwon.exchange.example.features.wallet.domain.Wallet
import com.switchwon.exchange.example.features.wallet.domain.WalletRepository
import com.switchwon.exchange.example.shared.domain.Currency
import com.switchwon.exchange.example.shared.domain.Money
import org.springframework.stereotype.Service

@Service
class InitializeWalletService(
    private val walletRepository: WalletRepository
) {

    /**
     * 회원의 지갑을 초기화합니다.
     * - KRW 지갑에 1,000,000원을 입금합니다.
     * - USD 지갑은 빈 상태로 생성합니다.
     */
    fun initializeWallet(member: Member) {
        val krwWallet = Wallet(
            member = member,
            currency = Currency.KRW
        ).apply { deposit(Money.krw(1_000_000L)) }

        val usdWallet = Wallet(
            member = member,
            currency = Currency.USD
        )

        val jpyWallet = Wallet(
            member = member,
            currency = Currency.JPY
        )

        walletRepository.saveAll(listOf(krwWallet, usdWallet, jpyWallet))
    }
}
