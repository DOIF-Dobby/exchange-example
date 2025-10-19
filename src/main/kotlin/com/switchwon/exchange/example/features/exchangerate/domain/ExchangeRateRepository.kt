package com.switchwon.exchange.example.features.exchangerate.domain

import com.switchwon.exchange.example.shared.domain.Currency
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ExchangeRateRepository : JpaRepository<ExchangeRate, Long> {

    @Query(
        """
        select er
        from ExchangeRate er
        where er.currency = :currency
        order by er.createdAt desc
        limit 1
    """
    )
    fun findLatestExchangeRate(currency: Currency): ExchangeRate?

    @Query(
        """
        select e
        from ExchangeRate e
        where e.createdAt = (
            select MAX(e2.createdAt)
            from ExchangeRate e2
            where e2.currency = e.currency
        )
    """
    )
    fun findLatestExchangeRates(): List<ExchangeRate>
}
