package com.switchwon.exchange.example.system.security

import org.springframework.security.core.context.SecurityContextHolder

class AuthenticationUtils {

    companion object {

        fun getTokenUser(): TokenUser? {
            return SecurityContextHolder.getContext().authentication?.principal as? TokenUser
        }

        fun getUserId(): Long? {
            return getTokenUser()?.userId
        }

        fun getUserEmail(): String? {
            return getTokenUser()?.email
        }
    }
}
