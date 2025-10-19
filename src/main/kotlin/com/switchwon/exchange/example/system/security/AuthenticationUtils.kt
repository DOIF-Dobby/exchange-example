package com.switchwon.exchange.example.system.security

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
    }
}
