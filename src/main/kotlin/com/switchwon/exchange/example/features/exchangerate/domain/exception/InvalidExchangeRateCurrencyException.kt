package com.switchwon.exchange.example.features.exchangerate.domain.exception

import com.switchwon.exchange.example.shared.domain.DomainException

class InvalidExchangeRateCurrencyException : DomainException(
    code = "INVALID_EXCHANGE_RATE_CURRENCY",
    message = "환율 정보의 통화는 KRW가 될 수 없습니다."
)
