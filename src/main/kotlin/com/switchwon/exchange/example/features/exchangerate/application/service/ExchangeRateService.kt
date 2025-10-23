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
    private val exchangeRateRepository: ExchangeRateRepository,
) {
    /**
     * 특정 통화의 최신 환율을 조회합니다.
     * - 환율이 존재하지 않으면 예외를 발생시킵니다.
     */
    @Transactional(readOnly = true)
    fun findLatestExchangeRate(currency: Currency): ExchangeRate {
        return exchangeRateRepository.findLatestExchangeRate(currency)
            ?: throw NotFoundException(code = "EXCHANGE_RATE_NOT_FOUND", message = "환율을 찾을 수 없습니다. 통화: $currency")
    }

    /**
     * 모든 통화의 최신 환율을 조회합니다.
     */
    @Transactional(readOnly = true)
    fun findLatestExchangeRateResponses(): List<ExchangeRateResponse> {
        return exchangeRateRepository
            .findLatestExchangeRates()
            .map { ExchangeRateResponse.from(it) }
    }

    /**
     * 모든 통화의 최신 환율을 Map 형태로 조회합니다.
     * - 통화는 키로, 환율은 값으로 매핑됩니다.
     */
    @Transactional(readOnly = true)
    fun findLatestExchangeRateMap(): Map<Currency, ExchangeRate> {
        return exchangeRateRepository
            .findLatestExchangeRates()
            .associateBy { it.currency }
    }

    /**
     * 새로운 환율을 생성합니다.
     */
    @Transactional
    fun createExchangeRate(currency: Currency, newRate: BigDecimal): ExchangeRate {
        // 이전 최신 환율 조회
        val latestExchangeRate = exchangeRateRepository.findLatestExchangeRate(currency)
        // 이전 환율이 없으면 새로운 환율을 그대로 사용
        val baseRate = latestExchangeRate?.rate ?: newRate

        return exchangeRateRepository.save(
            ExchangeRate(
                currency = currency,
                rate = newRate,
                baseRate = baseRate,
            ),
        )
    }
}
