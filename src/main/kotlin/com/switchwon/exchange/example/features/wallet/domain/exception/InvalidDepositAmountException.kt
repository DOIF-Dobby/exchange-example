package com.switchwon.exchange.example.features.wallet.domain.exception

import com.switchwon.exchange.example.shared.domain.exception.DomainException

class InvalidDepositAmountException :
    DomainException(
        code = "INVALID_DEPOSIT_AMOUNT",
        message = "입금 금액이 유효하지 않습니다.",
    )
