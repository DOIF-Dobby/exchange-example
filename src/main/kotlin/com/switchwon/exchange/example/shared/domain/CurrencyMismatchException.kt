package com.switchwon.exchange.example.shared.domain

class CurrencyMismatchException : DomainException(
    code = "CURRENCY_MISMATCH",
    message = "통화 타입이 일치하지 않습니다."
)
