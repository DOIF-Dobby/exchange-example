package com.switchwon.exchange.example.features.order.application.dto

import com.switchwon.exchange.example.features.exchangerate.domain.ExchangeRateId
import com.switchwon.exchange.example.shared.domain.Currency
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Digits
import java.math.BigDecimal

@Schema(description = "환전 주문 요청 DTO")
data class OrderRequest(
    @field:Schema(description = "환율 ID", required = true, example = "100")
    val exchangeRateId: ExchangeRateId,
    @field:Schema(description = "매수 통화", required = true, example = "KRW")
    val fromCurrency: Currency,
    @field:Schema(description = "매도 통화", required = true, example = "USD")
    val toCurrency: Currency,

    @field:Schema(description = "주문 금액", required = true, example = "10000.00")
    @field:DecimalMin(value = "0.01", message = "주문금액은 0보다 커야 합니다.")
    @field:Digits(integer = 12, fraction = 2, message = "주문금액은 최대 소수점 2자리까지 입력 가능합니다.")
    val forexAmount: BigDecimal,
)
