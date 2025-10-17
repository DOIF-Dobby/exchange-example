package com.switchwon.exchange.example.features.auth.application.dto

data class TokenResponse(
    val memberId: Long,
    val token: String,
)
