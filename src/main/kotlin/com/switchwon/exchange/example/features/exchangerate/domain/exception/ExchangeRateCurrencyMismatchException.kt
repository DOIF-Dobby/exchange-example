package com.switchwon.exchange.example.features.exchangerate.domain.exception

import com.switchwon.exchange.example.shared.domain.Currency
import com.switchwon.exchange.example.shared.domain.exception.DomainException

class ExchangeRateCurrencyMismatchException(
    expected: Currency,
    actual: Currency,
) : DomainException(
        code = "EXCHANGE_RATE_CURRENCY_MISMATCH",
        message = "환율의 대상 통화($expected)와 변환하려는 금액의 통화($actual)가 일치하지 않습니다.",
    )
