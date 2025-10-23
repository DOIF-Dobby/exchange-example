package com.switchwon.exchange.example.system.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.switchwon.exchange.example.system.core.logging.log
import com.switchwon.exchange.example.system.web.ApiResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

@Component
class SimpleAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper,
) : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException,
    ) {
        log.warn { "Request: [ ${request.method} ${request.requestURI} ] Unauthorized" }

        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = StandardCharsets.UTF_8.name()
        response.status = HttpServletResponse.SC_UNAUTHORIZED

        objectMapper.writeValue(
            response.writer,
            ApiResponse.fail(
                code = "UNAUTHORIZED",
                message = "인증되지 않은 요청입니다.",
            ),
        )
    }
}
