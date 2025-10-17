package com.switchwon.exchange.example.features.exchangerate.application.service

import com.switchwon.exchange.example.features.exchangerate.application.dto.ExchangeRateResponse
import com.switchwon.exchange.example.features.exchangerate.domain.ExchangeRate
import com.switchwon.exchange.example.features.exchangerate.domain.ExchangeRateRepository
import com.switchwon.exchange.example.shared.domain.Currency
import com.switchwon.exchange.example.system.web.exception.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class ExchangeRateService(
    private val exchangeRateRepository: ExchangeRateRepository
) {
    
    /**
     * 특정 통화의 최신 환율을 조회합니다.
     * - 환율이 존재하지 않으면 예외를 발생시킵니다.
     */
    @Transactional(readOnly = true)
    fun findLatestExchangeRate(currency: Currency): ExchangeRate {
        return exchangeRateRepository.findLatestExchangeRate(currency)
            ?: throw NotFoundException("환율 정보를 찾을 수 없습니다. 통화: $currency")
    }

    /**
     * 모든 통화의 최신 환율을 조회합니다.
     */
    @Transactional(readOnly = true)
    fun findLatestExchangeRateResponses(): List<ExchangeRateResponse> {
        return exchangeRateRepository.findLatestExchangeRate()
            .map { ExchangeRateResponse.from(it) }
    }

    /**
     * 새로운 환율을 생성합니다.
     */
    @Transactional
    fun createExchangeRate(currency: Currency, rate: BigDecimal): ExchangeRate {
        return exchangeRateRepository.save(
            ExchangeRate(
                currency = currency,
                rate = rate
            )
        )
    }
}
