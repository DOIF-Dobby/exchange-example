package com.switchwon.exchange.example.system

import com.switchwon.exchange.example.system.security.AuthorizationHeaderJwtFilter
import com.switchwon.exchange.example.system.security.SimpleAuthenticationEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher

@Configuration
class SecurityConfig {

    private val permitAllRequest: Array<RequestMatcher> = arrayOf(
        PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/auth/login"),
        PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.GET, "/swagger-ui/**"),
        PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.GET, "/v3/api-docs/**"),
    )

    @Bean
    fun securityFilterChain(
        httpSecurity: HttpSecurity,
        authorizationHeaderJwtFilter: AuthorizationHeaderJwtFilter,
        simpleAuthenticationEntryPoint: SimpleAuthenticationEntryPoint
    ): SecurityFilterChain {
        httpSecurity
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .formLogin { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.requestMatchers(*permitAllRequest).permitAll()
                it.anyRequest().authenticated()
            }
            .addFilterBefore(authorizationHeaderJwtFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling {
                it.authenticationEntryPoint(simpleAuthenticationEntryPoint)
            }

        return httpSecurity.build()
    }
}
