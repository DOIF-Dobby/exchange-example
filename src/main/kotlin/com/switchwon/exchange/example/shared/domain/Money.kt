package com.switchwon.exchange.example.shared.domain

import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.math.BigDecimal

@Embeddable
class Money(
    @Enumerated(EnumType.STRING)
    val currency: Currency,
    amount: BigDecimal,
) : Comparable<Money> {

    // 통화 타입에 맞는 자릿수 만큼 roundingMode 적용해서 금액 설정
    val amount: BigDecimal = amount.setScale(currency.scale, currency.roundingMode)

    fun isSameCurrency(other: Money) = currency == other.currency

    fun isZero(): Boolean = amount.compareTo(BigDecimal.ZERO) == 0
    fun isPositive(): Boolean = amount > BigDecimal.ZERO
    fun isNegative(): Boolean = amount < BigDecimal.ZERO

    fun formatted(): String {
        val scaledAmount = amount.setScale(currency.scale, currency.roundingMode)
        return "$currency ${currency.decimalFormat.format(scaledAmount)}"
    }

    operator fun plus(other: Money) = operateMoney(other) { a, b -> a + b }
    operator fun minus(other: Money) = operateMoney(other) { a, b -> a - b }
    operator fun times(other: Number) = Money(currency, amount * BigDecimal(other.toString()))
    operator fun div(other: Number) = Money(currency, amount.divide(BigDecimal(other.toString()), currency.scale, currency.roundingMode))

    /**
     * 두 Money 객체의 통화가 같은지 검증
     */
    private fun validateSameCurrency(other: Money) {
        require(currency == other.currency) { throw CurrencyMismatchException() }
    }

    /**
     * 두 Money 객체의 통화가 같은지 검증 후 사칙연산 수행
     */
    private fun operateMoney(other: Money, operator: (BigDecimal, BigDecimal) -> BigDecimal): Money {
        validateSameCurrency(other)
        return Money(currency, operator(amount, other.amount))
    }

    override fun compareTo(other: Money): Int {
        validateSameCurrency(other)
        return amount.compareTo(other.amount)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Money

        return currency == other.currency && amount.compareTo(other.amount) == 0
    }

    override fun toString(): String {
        return formatted()
    }

    override fun hashCode(): Int {
        var result = currency.hashCode()
        result = 31 * result + amount.hashCode()
        return result
    }

    companion object {
        fun of(currency: Currency, amount: BigDecimal) = Money(currency, amount)

        fun krw(amount: BigDecimal) = Money(Currency.KRW, amount)
        fun krw(amount: Number) = Money(Currency.KRW, BigDecimal(amount.toString()))

        fun usd(amount: BigDecimal) = Money(Currency.USD, amount)
        fun usd(amount: Number) = Money(Currency.USD, BigDecimal(amount.toString()))
    }
}
