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

@Table(
    name = "exchange_rate",
    indexes = [
        Index(name = "idx_exchange_rate_currency_created_at", columnList = "currency, createdAt DESC")
    ]
)
@Entity
class ExchangeRate(
    @Column(name = "currency", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    val currency: Currency,

    @Column(name = "rate", nullable = false, updatable = false)
    val rate: BigDecimal,
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
