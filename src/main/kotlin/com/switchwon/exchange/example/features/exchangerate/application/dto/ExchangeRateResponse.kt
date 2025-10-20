package com.switchwon.exchange.example.features.exchangerate.application.dto

import com.switchwon.exchange.example.features.exchangerate.domain.ExchangeRate
import com.switchwon.exchange.example.shared.domain.Currency
import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Schema(description = "환율 응답 DTO")
data class ExchangeRateResponse(
    @field:Schema(description = "환율 ID", required = true, example = "100")
    val exchangeRateId: Long,
    @field:Schema(description = "통화", required = true, example = "USD")
    val currency: Currency,
    @field:Schema(description = "환율", required = true, example = "1450.25")
    val rate: BigDecimal,
    @field:Schema(description = "변동률", required = true, example = "0.25")
    val changePercentage: BigDecimal,
    @field:Schema(description = "적용 일시", required = true, example = "2023-10-01T12:00:00")
    val applyDateTime: LocalDateTime,
) {
    companion object {
        fun from(exchangeRate: ExchangeRate) = exchangeRate.run {
            ExchangeRateResponse(
                exchangeRateId = id.value,
                currency = currency,
                rate = rate,
                changePercentage = changePercentage,
                applyDateTime = createdAt!!.truncatedTo(ChronoUnit.SECONDS),
            )
        }
    }
}
