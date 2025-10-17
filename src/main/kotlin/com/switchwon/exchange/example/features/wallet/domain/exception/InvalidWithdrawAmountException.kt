package com.switchwon.exchange.example.features.wallet.domain.exception

import com.switchwon.exchange.example.shared.domain.DomainException

class InvalidWithdrawAmountException : DomainException(
    code = "INVALID_WITHDRAW_AMOUNT",
    message = "출금 금액이 유효하지 않습니다."
)
