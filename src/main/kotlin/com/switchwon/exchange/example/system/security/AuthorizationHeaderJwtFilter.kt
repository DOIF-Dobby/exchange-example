package com.switchwon.exchange.example.system.security

import com.switchwon.exchange.example.system.core.logging.log
import com.switchwon.exchange.example.system.security.jwt.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class AuthorizationHeaderJwtFilter(
    private val jwtService: JwtService,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            val token = extractTokenFromHeader(request)
            if (!token.isNullOrEmpty() && jwtService.validateToken(token)) {
                val tokenUser = convertTokenToUser(token)
                val tokenAuthentication = TokenAuthentication(tokenUser)
                SecurityContextHolder.getContext().authentication = tokenAuthentication
            }
        } catch (e: Exception) {
            log.error(e) { "Error processing request: ${e.message}" }
        }

        filterChain.doFilter(request, response)
    }

    /**
     * HttpServletRequest에서 Authorization 헤더를 추출하여 JWT 토큰을 반환합니다.
     */
    private fun extractTokenFromHeader(request: HttpServletRequest): String? {
        val authorization = request.getHeader(HttpHeaders.AUTHORIZATION)

        return if (authorization != null && authorization.startsWith("Bearer ")) {
            authorization.substring(7)
        } else {
            null
        }
    }

    /**
     *JWT 토큰을 TokenUser로 변환합니다.
     */
    private fun convertTokenToUser(token: String): TokenMember {
        val claims = jwtService.getClaims(token)
        val userId = claims.payload.subject ?: throw IllegalArgumentException("User ID not found in token claims")
        val email =
            claims.payload.get("email", String::class.java)
                ?: throw IllegalArgumentException("Email not found in token claims")

        return TokenMember(userId.toLong(), email)
    }
}
