package com.switchwon.exchange.example.features.wallet.application.dto

import java.math.BigDecimal

data class WalletSummaryResponse(
    val totalKrwBalance: BigDecimal,
    val wallets: List<WalletResponse>
)
