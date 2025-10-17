package com.switchwon.exchange.example.system.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtService(
    private val properties: JwtProperties,
) {

    private val secretKey: SecretKey by lazy {
        Keys.hmacShaKeyFor(properties.key.toByteArray(StandardCharsets.UTF_8))
    }

    /**
     * 토큰을 생성한다.
     */
    fun generateToken(subject: String, claims: Map<String, Any>): String {
        val issuedAt = Date()
        val expiredMills = issuedAt.time + properties.expiration.toMillis()
        val expiration = Date(expiredMills)

        return Jwts.builder()
            .subject(subject)
            .claims(claims)
            .issuedAt(issuedAt)
            .expiration(expiration)
            .signWith(secretKey)
            .compact()
    }

    /**
     * 토큰이 유효한지 검증하고 반환한다.
     * 이 과정에서 서명, 만료 시간 등이 올바르지 않으면 예외 발생
     */
    fun getClaims(token: String): Jws<Claims> {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
    }

    /**
     * 토큰이 유효한지 검증한다.
     * 유효하지 않으면 false를 반환
     */
    fun validateToken(token: String): Boolean {
        return try {
            getClaims(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * 토큰에서 subject를 추출한다.
     */
    fun getSubject(token: String): String {
        return getClaims(token).payload.subject
    }
}
