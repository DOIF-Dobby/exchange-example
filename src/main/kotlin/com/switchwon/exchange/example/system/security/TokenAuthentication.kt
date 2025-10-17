package com.switchwon.exchange.example.system.security

import org.springframework.security.authentication.AbstractAuthenticationToken

class TokenAuthentication(
    private val principal: TokenUser,
) : AbstractAuthenticationToken(emptyList()) {

    init {
        isAuthenticated = true
    }

    override fun getPrincipal(): Any = principal

    override fun getCredentials(): Any? = null

}
