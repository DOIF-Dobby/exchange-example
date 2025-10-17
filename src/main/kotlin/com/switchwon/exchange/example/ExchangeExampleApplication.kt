package com.switchwon.exchange.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableScheduling

@EnableJpaAuditing
@EnableScheduling
@ConfigurationPropertiesScan
@SpringBootApplication
class ExchangeExampleApplication

fun main(args: Array<String>) {
    runApplication<ExchangeExampleApplication>(*args)
}
