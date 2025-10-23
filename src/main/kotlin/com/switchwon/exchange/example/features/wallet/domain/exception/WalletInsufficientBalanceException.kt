package com.switchwon.exchange.example.features.wallet.domain.exception

import com.switchwon.exchange.example.shared.domain.exception.DomainException

class WalletInsufficientBalanceException :
    DomainException(
        code = "WALLET_INSUFFICIENT_BALANCE",
        message = "지갑의 잔액이 부족합니다.",
    )
