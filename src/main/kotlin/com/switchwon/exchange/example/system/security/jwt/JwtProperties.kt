package com.switchwon.exchange.example.system.security.jwt

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val key: String,
    val expiration: Duration,
)
