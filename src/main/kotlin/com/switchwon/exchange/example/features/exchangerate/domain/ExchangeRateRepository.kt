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
    """,
    )
    fun findLatestExchangeRate(currency: Currency): ExchangeRate?

    @Query(
        """
            select er1
            from ExchangeRate er1
            inner join (
                select
                    er2.currency as currency,
                    max(er2.applyDateTime) as maxApplyTime
                from
                    ExchangeRate er2
                group by
                    er2.currency
            ) latest on er1.currency = latest.currency
                    and er1.applyDateTime = latest.maxApplyTime
        """,
    )
    fun findLatestExchangeRates(): List<ExchangeRate>
}
