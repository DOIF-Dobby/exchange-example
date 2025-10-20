package com.switchwon.exchange.example.features.auth.application.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email

@Schema(description = "로그인 요청 DTO")
data class LoginRequest(
    @field:Schema(description = "이메일", required = true, example = "example@switchwon.com")
    @field:Email(message = "유효한 이메일 형식이 아닙니다.", regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    val email: String,
)
