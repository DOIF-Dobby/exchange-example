package com.switchwon.exchange.example.features.auth.api

import com.switchwon.exchange.example.features.auth.application.AuthService
import com.switchwon.exchange.example.features.auth.application.dto.TokenResponse
import com.switchwon.exchange.example.system.web.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Auth API", description = "인증 관련 API")
@RestController
class AuthController(
    private val authService: AuthService,
) {

    /**
     * 로그인 요청을 처리합니다.
     * 이메일로 회원을 조회하고, 없으면 새로 등록합니다.
     * 로그인 성공 시 토큰을 반환합니다.
     */
    @Operation(
        summary = "로그인",
        description = """
        이메일로 로그인합니다.
        - 회원이 존재하지 않으면 새로 등록합니다.
        - 회원이 존재하면 해당 회원으로 로그인합니다.
        - 최초 로그인 시 회원의 지갑이 생성됩니다. 잔액은 KRW: 1,000,000 / USD: O / JPY: 0 으로 초기화됩니다.
        - 로그인 성공 시 JWT 인증 토큰을 반환합니다.
    """
    )
    @PostMapping("/auth/login")
    fun login(@RequestParam email: String): ApiResponse<TokenResponse> {
        val tokenResponse = authService.login(email)
        return ApiResponse.ok(tokenResponse)
    }
}
