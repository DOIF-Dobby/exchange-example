package com.switchwon.exchange.example.features.exchangerate.domain

import com.switchwon.exchange.example.features.exchangerate.domain.exception.ExchangeRateCurrencyMismatchException
import com.switchwon.exchange.example.features.exchangerate.domain.exception.ForexConversionUnsupportedCurrencyException
import com.switchwon.exchange.example.features.exchangerate.domain.exception.InvalidExchangeRateCurrencyException
import com.switchwon.exchange.example.features.exchangerate.domain.exception.KrwConversionUnsupportedCurrencyException
import com.switchwon.exchange.example.shared.domain.Currency
import com.switchwon.exchange.example.shared.domain.Money
import com.switchwon.exchange.example.system.data.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime

@Table(
    name = "exchange_rate",
    indexes = [
        Index(name = "idx_exchange_rate_currency_apply_date_time", columnList = "currency, applyDateTime DESC"),
    ],
)
@Entity
class ExchangeRate(
    @Column(name = "currency", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    val currency: Currency,

    @Column(name = "rate", nullable = false, updatable = false)
    val rate: BigDecimal,

    @Column(name = "base_rate", nullable = false, updatable = false)
    val baseRate: BigDecimal,

    @Column(name = "apply_date_time", nullable = false, updatable = false)
    val applyDateTime: LocalDateTime,
) : BaseEntity() {

    init {
        if (currency == Currency.KRW) {
            throw InvalidExchangeRateCurrencyException()
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchange_rate_id")
    val id: ExchangeRateId = ExchangeRateId(0L)

    @Column(name = "change_percentage", nullable = false, updatable = false)
    val changePercentage: BigDecimal = calculateChangePercentage()

    /**
     * 생성 시점에 변동률을 계산합니다.
     */
    private fun calculateChangePercentage(): BigDecimal {
        if (baseRate.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO
        }

        val change = this.rate - this.baseRate
        val percentage = change.divide(this.baseRate, 8, RoundingMode.HALF_UP) * BigDecimal(100)

        // 최종 결과를 소수점 2자리로 반올림하여 반환합니다.
        return percentage.setScale(2, RoundingMode.HALF_UP)
    }

    /**
     * 원화를 이 환율의 대상 통화로 변환합니다. (KRW -> Forex)
     */
    fun convertKrwToForex(krwAmount: Money): Money {
        if (!krwAmount.currency.isKrw()) {
            throw KrwConversionUnsupportedCurrencyException(krwAmount.currency)
        }

        val convertedAmount = (krwAmount / rate) * currency.unit
        return Money(currency, convertedAmount.amount)
    }

    /**
     * 이 환율의 대상 통화를 원화로 변환합니다. (Forex -> KRW)
     */
    fun convertForexToKrw(forexAmount: Money): Money {
        if (forexAmount.currency.isKrw()) {
            throw ForexConversionUnsupportedCurrencyException()
        }

        if (forexAmount.currency != currency) {
            throw ExchangeRateCurrencyMismatchException(expected = currency, actual = forexAmount.currency)
        }

        val convertedAmount = (forexAmount / currency.unit) * rate
        return Money(Currency.KRW, convertedAmount.amount)
    }
}
