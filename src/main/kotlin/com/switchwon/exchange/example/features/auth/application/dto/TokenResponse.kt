package com.switchwon.exchange.example.features.auth.application.dto

import io.swagger.v3.oas.annotations.media.Schema

@Suppress("ktlint:standard:max-line-length")
@Schema(description = "인증 토큰 응답 DTO")
data class TokenResponse(
    @field:Schema(description = "회원 ID", required = true, example = "1")
    val memberId: Long,
    @field:Schema(
        description = "JWT 인증 토큰",
        required = true,
        example = "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiIxIiwiZW1haWwiOiJleGFtcGxlQHN3aXRjaHdvbi5jb20iLCJpYXQiOjE3NjA5MzMzMjUsImV4cCI6MTc2MTAxOTcyNX0.sRrR617w6fHYYQ3Uxv39hqcTz3ihfpN442dnw6RMbpsXviVilKkgY3A5FsuG8IEL",
    )
    val token: String,
)
