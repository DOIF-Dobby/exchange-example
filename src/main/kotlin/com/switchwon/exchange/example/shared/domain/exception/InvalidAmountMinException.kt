package com.switchwon.exchange.example.shared.domain.exception

import com.switchwon.exchange.example.shared.domain.Currency
import java.math.BigDecimal

class InvalidAmountMinException(
    val currency: Currency,
    minOrderAmount: BigDecimal,
    actualAmount: BigDecimal,
) : DomainException(
        code = "INVALID_AMOUNT_MIN",
        message = "$currency 통화는 최소 주문 금액이 $minOrderAmount 이상이어야 합니다. 입력된 금액: $actualAmount",
    )
