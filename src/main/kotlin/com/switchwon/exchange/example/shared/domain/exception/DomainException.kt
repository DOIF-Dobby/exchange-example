package com.switchwon.exchange.example.shared.domain.exception

abstract class DomainException(
    val code: String,
    message: String,
) : RuntimeException(message)
