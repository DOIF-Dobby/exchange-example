package com.switchwon.exchange.example.features.wallet.application.dto

import com.switchwon.exchange.example.features.wallet.domain.Wallet
import com.switchwon.exchange.example.shared.domain.Currency
import java.math.BigDecimal

data class WalletResponse(
    val walletId: Long,
    val currency: Currency,
    val balance: BigDecimal,
) {
    companion object {
        fun from(wallet: Wallet) = wallet.run {
            WalletResponse(
                walletId = id.value,
                currency = balance.currency,
                balance = balance.amount
            )
        }
    }
}
