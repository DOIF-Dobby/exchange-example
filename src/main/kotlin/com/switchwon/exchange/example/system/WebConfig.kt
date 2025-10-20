package com.switchwon.exchange.example.system

import com.switchwon.exchange.example.system.web.filter.LoggingFilter
import jakarta.servlet.Filter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

    @Bean
    fun loggingFilter(): Filter {
        return LoggingFilter()
    }
}
