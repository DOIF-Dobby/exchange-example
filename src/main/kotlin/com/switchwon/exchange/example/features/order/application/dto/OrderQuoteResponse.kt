package com.switchwon.exchange.example.features.order.application.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

@Schema(description = "환전 주문 견적 응답 DTO")
data class OrderQuoteResponse(
    @field:Schema(description = "원화 금액", required = true, example = "10000.00")
    val krwAmount: BigDecimal,
    @field:Schema(description = "적용된 환율", required = true, example = "1450.25")
    val appliedRate: BigDecimal,
)
