package com.switchwon.exchange.example.features.auth.api

import com.switchwon.exchange.example.features.auth.application.AuthService
import com.switchwon.exchange.example.features.auth.application.dto.TokenResponse
import com.switchwon.exchange.example.system.web.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authService: AuthService,
) {

    /**
     * 로그인 요청을 처리합니다.
     * 이메일로 회원을 조회하고, 없으면 새로 등록합니다.
     * 로그인 성공 시 토큰을 반환합니다.
     */
    @PostMapping("/auth/login")
    fun login(@RequestParam email: String): ApiResponse<TokenResponse> {
        val tokenResponse = authService.login(email)
        return ApiResponse.ok(tokenResponse)
    }
}
