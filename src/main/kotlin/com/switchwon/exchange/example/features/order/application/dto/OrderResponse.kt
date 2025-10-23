package com.switchwon.exchange.example.features.order.application.dto

import com.switchwon.exchange.example.features.order.domain.Order
import com.switchwon.exchange.example.shared.domain.Currency
import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Schema(description = "환전 주문 응답 DTO")
data class OrderResponse(
    @field:Schema(description = "주문 ID", required = true, example = "100")
    val orderId: Long,
    @field:Schema(description = "매수 통화", required = true, example = "KRW")
    val fromCurrency: Currency,
    @field:Schema(description = "매수 금액", required = true, example = "10000.00")
    val fromAmount: BigDecimal,
    @field:Schema(description = "매도 통화", required = true, example = "USD")
    val toCurrency: Currency,
    @field:Schema(description = "매도 금액", required = true, example = "100.00")
    val toAmount: BigDecimal,
    @field:Schema(description = "적용된 환율", required = true, example = "1450.00")
    val appliedRate: BigDecimal,
    @field:Schema(description = "주문 생성 시간", required = true, example = "2023-10-01T12:00:00")
    val orderedAt: LocalDateTime,
) {
    companion object {
        fun from(order: Order) =
            order.run {
                OrderResponse(
                    orderId = id.value,
                    fromCurrency = fromMoney.currency,
                    fromAmount = fromMoney.amount,
                    toCurrency = toMoney.currency,
                    toAmount = toMoney.amount,
                    appliedRate = appliedRate,
                    orderedAt = createdAt!!.truncatedTo(ChronoUnit.SECONDS),
                )
            }
    }
}
