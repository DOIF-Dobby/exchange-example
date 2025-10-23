package com.switchwon.exchange.example.system.security

import com.switchwon.exchange.example.features.member.domain.MemberId
import com.switchwon.exchange.example.system.web.exception.UnauthorizedException
import org.springframework.security.core.context.SecurityContextHolder

class AuthenticationUtils {
    companion object {
        fun getCurrentMember(): TokenMember? {
            return SecurityContextHolder.getContext().authentication?.principal as? TokenMember
        }

        fun getCurrentMemberId(): Long? {
            return getCurrentMember()?.memberId
        }

        fun getCurrentMemberEmail(): String? {
            return getCurrentMember()?.email
        }

        fun requireCurrentMemberId(): MemberId {
            return getCurrentMemberId()?.let { MemberId(it) }
                ?: throw UnauthorizedException()
        }
    }
}
