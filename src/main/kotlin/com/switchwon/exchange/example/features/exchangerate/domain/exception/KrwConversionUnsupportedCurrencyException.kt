package com.switchwon.exchange.example.features.exchangerate.domain.exception

import com.switchwon.exchange.example.shared.domain.Currency
import com.switchwon.exchange.example.shared.domain.exception.DomainException

class KrwConversionUnsupportedCurrencyException(
    currency: Currency,
) : DomainException(
        code = "UNSUPPORTED_CURRENCY_FOR_KRW_CONVERSION",
        message = "원화(KRW) 변환은 KRW 통화만 지원합니다. 입력된 통화: $currency",
    )
