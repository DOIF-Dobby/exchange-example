package com.switchwon.exchange.example.system.web.filter

import com.switchwon.exchange.example.system.core.logging.log
import com.switchwon.exchange.example.system.security.AuthenticationUtils
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher
import org.springframework.web.filter.AbstractRequestLoggingFilter

class LoggingFilter : AbstractRequestLoggingFilter() {
    override fun beforeRequest(request: HttpServletRequest, message: String) {
        val memberId = AuthenticationUtils.getCurrentMemberId()
        val memberEmail = AuthenticationUtils.getCurrentMemberEmail()

        if (isLoggable(request)) {
            log.info { "$message >> memberId: $memberId, memberEmail: $memberEmail" }
        }
    }

    override fun afterRequest(request: HttpServletRequest, message: String) {
        val memberId = AuthenticationUtils.getCurrentMemberId()
        val memberEmail = AuthenticationUtils.getCurrentMemberEmail()

        if (isLoggable(request)) {
            log.info { "$message >> memberId: $memberId, memberEmail: $memberEmail" }
        }
    }

    private fun isLoggable(request: HttpServletRequest): Boolean {
        return exclusivePaths
            .stream()
            .noneMatch {
                PathPatternRequestMatcher
                    .withDefaults()
                    .matcher(it)
                    .matches(request)
            }
    }

    companion object {
        private val exclusivePaths =
            listOf(
                "/actuator",
                "/error",
                "/favicon.ico",
                "/static",
                "/public",
                "/resources",
                "/webjars",
                "/swagger-ui/**",
                "/v3/api-docs/**",
            )
    }
}
