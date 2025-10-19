package com.switchwon.exchange.example.features.order.application.dto

import com.switchwon.exchange.example.features.exchangerate.domain.ExchangeRateId
import com.switchwon.exchange.example.shared.domain.Currency
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Digits
import java.math.BigDecimal

data class OrderRequest(
    val exchangeRateId: ExchangeRateId,
    val fromCurrency: Currency,
    val toCurrency: Currency,

    @field:DecimalMin(value = "0.01", message = "주문금액은 0보다 커야 합니다.")
    @field:Digits(integer = 12, fraction = 2, message = "주문금액은 최대 소수점 2자리까지 입력 가능합니다.")
    val forexAmount: BigDecimal,
)
