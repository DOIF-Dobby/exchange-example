package com.switchwon.exchange.example.shared.domain

abstract class DomainException(
    val code: String,
    message: String
) : RuntimeException(message)
