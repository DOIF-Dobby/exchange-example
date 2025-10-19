package com.switchwon.exchange.example.features.exchangerate.application.dto

import com.switchwon.exchange.example.features.exchangerate.domain.ExchangeRate
import com.switchwon.exchange.example.shared.domain.Currency
import java.math.BigDecimal
import java.time.LocalDateTime

data class ExchangeRateResponse(
    val exchangeRateId: Long,
    val currency: Currency,
    val rate: BigDecimal,
    val changePercentage: BigDecimal,
    val applyDateTime: LocalDateTime,
) {
    companion object {
        fun from(exchangeRate: ExchangeRate) = exchangeRate.run {
            ExchangeRateResponse(
                exchangeRateId = id.value,
                currency = currency,
                rate = rate,
                changePercentage = changePercentage,
                applyDateTime = createdAt!!,
            )
        }
    }
}
