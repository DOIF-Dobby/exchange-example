package com.switchwon.exchange.example.features.wallet.application.dto

import com.switchwon.exchange.example.features.wallet.domain.Wallet
import com.switchwon.exchange.example.shared.domain.Currency
import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

@Schema(description = "지갑 응답 DTO")
data class WalletResponse(
    @field:Schema(description = "지갑 ID", required = true, example = "1")
    val walletId: Long,
    @field:Schema(description = "통화", required = true, example = "KRW")
    val currency: Currency,
    @field:Schema(description = "잔액", required = true, example = "1000000.00")
    val balance: BigDecimal,
) {
    companion object {
        fun from(wallet: Wallet) = wallet.run {
            WalletResponse(
                walletId = id.value,
                currency = currency,
                balance = balanceAmount
            )
        }
    }
}
