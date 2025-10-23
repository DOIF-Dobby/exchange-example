package com.switchwon.exchange.example.features.wallet.application.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

@Schema(description = "지갑 요약 응답 DTO")
data class WalletSummaryResponse(
    @field:Schema(description = "총 원화 잔액", required = true, example = "1000000.00")
    val totalKrwBalance: BigDecimal,
    @field:Schema(description = "지갑 목록", required = true)
    val wallets: List<WalletResponse>,
)
