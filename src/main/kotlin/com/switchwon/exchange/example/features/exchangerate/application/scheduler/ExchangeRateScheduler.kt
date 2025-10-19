package com.switchwon.exchange.example.features.exchangerate.application.scheduler

import com.switchwon.exchange.example.features.exchangerate.application.service.ExchangeRateService
import com.switchwon.exchange.example.shared.domain.Currency
import com.switchwon.exchange.example.system.core.logging.log
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.math.BigDecimal
import kotlin.random.Random

@Component
class ExchangeRateScheduler(
    private val exchangeRateService: ExchangeRateService
) {

    @Scheduled(cron = "0 * * * * *")
    fun triggerCreateNewExchangeRate() {
        generateRandomUsd().also {
            log.info { "random USD rate: $it" }

            exchangeRateService.createExchangeRate(
                currency = Currency.USD,
                newRate = it
            )
        }

        generateRandomJpy().also {
            log.info { "random JPY rate: $it" }

            exchangeRateService.createExchangeRate(
                currency = Currency.JPY,
                newRate = it
            )
        }
    }

    private fun generateRandomUsd(): BigDecimal {
        val nextDouble = Random.nextDouble(1450.0, 1500.0)
        return BigDecimal(nextDouble).setScale(Currency.USD.roundingScale, Currency.USD.roundingMode)
    }

    private fun generateRandomJpy(): BigDecimal {
        val nextDouble = Random.nextDouble(900.0, 950.0)
        return BigDecimal(nextDouble).setScale(Currency.JPY.roundingScale, Currency.JPY.roundingMode)
    }
}
