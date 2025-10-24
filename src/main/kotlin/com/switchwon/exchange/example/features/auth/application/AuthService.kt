package com.switchwon.exchange.example.features.auth.application

import com.switchwon.exchange.example.features.auth.application.dto.TokenResponse
import com.switchwon.exchange.example.features.member.application.service.MemberService
import com.switchwon.exchange.example.features.member.domain.Member
import com.switchwon.exchange.example.system.security.jwt.JwtService
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val memberService: MemberService,
    private val jwtService: JwtService,
) {

    /**
     * 이메일로 회원을 조회하고, 없으면 새로 등록합니다.
     * JWT 토큰을 생성하여 반환합니다.
     */
    fun login(email: String): TokenResponse {
        val member = memberService.findOrRegister(email)
        val token = generateTokenByMember(member)

        return TokenResponse(
            memberId = member.id.value,
            token = token,
        )
    }

    /**
     * 회원 정보를 기반으로 JWT 토큰을 생성합니다.
     */
    private fun generateTokenByMember(member: Member): String {
        return jwtService.generateToken(
            subject = member.id.value.toString(),
            claims =
                mapOf(
                    "email" to member.email,
                ),
        )
    }
}
