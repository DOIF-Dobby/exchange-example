package com.switchwon.exchange.example.features.exchangerate.domain.exception

import com.switchwon.exchange.example.shared.domain.exception.DomainException

class ForexConversionUnsupportedCurrencyException :
    DomainException(
        code = "UNSUPPORTED_FOREX_CONVERSION_CURRENCY",
        message = "외화 변환은 원화(KRW)를 지원하지 않습니다.",
    )
