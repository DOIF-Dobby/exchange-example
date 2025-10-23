package com.switchwon.exchange.example.shared.domain.exception

import com.switchwon.exchange.example.shared.domain.Currency

class InvalidAmountScaleException(
    val currency: Currency,
    expectedScale: Int,
    actualScale: Int,
) : DomainException(
        code = "INVALID_AMOUNT_SCALE",
        message = "$currency 통화는 소수점 ${expectedScale}자리까지만 허용됩니다. 입력된 소수점 자릿수: $actualScale",
    )
