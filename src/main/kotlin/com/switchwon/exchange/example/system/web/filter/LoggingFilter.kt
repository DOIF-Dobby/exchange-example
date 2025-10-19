package com.switchwon.exchange.example.system.web.filter

import com.switchwon.exchange.example.system.core.logging.log
import com.switchwon.exchange.example.system.security.AuthenticationUtils
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.filter.AbstractRequestLoggingFilter

class LoggingFilter : AbstractRequestLoggingFilter() {
    override fun beforeRequest(request: HttpServletRequest, message: String) {
        val memberId = AuthenticationUtils.getCurrentMemberId()
        val memberEmail = AuthenticationUtils.getCurrentMemberEmail()

        log.info { "$message >> memberId: ${memberId}, memberEmail: $memberEmail" }
    }

    override fun afterRequest(request: HttpServletRequest, message: String) {
        val memberId = AuthenticationUtils.getCurrentMemberId()
        val memberEmail = AuthenticationUtils.getCurrentMemberEmail()

        log.info { "$message >> memberId: ${memberId}, memberEmail: $memberEmail" }
    }
}
