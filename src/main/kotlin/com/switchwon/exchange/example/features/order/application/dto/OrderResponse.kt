package com.switchwon.exchange.example.features.order.application.dto

import com.switchwon.exchange.example.features.order.domain.Order
import com.switchwon.exchange.example.shared.domain.Currency
import java.math.BigDecimal
import java.time.LocalDateTime

data class OrderResponse(
    val orderId: Long,
    val fromCurrency: Currency,
    val fromAmount: BigDecimal,
    val toCurrency: Currency,
    val toAmount: BigDecimal,
    val appliedRate: BigDecimal,
    val orderedAt: LocalDateTime,
) {
    companion object {
        fun from(order: Order) = order.run {
            OrderResponse(
                orderId = id.value,
                fromCurrency = fromMoney.currency,
                fromAmount = fromMoney.amount,
                toCurrency = toMoney.currency,
                toAmount = toMoney.amount,
                appliedRate = appliedRate,
                orderedAt = createdAt!!
            )
        }
    }
}
