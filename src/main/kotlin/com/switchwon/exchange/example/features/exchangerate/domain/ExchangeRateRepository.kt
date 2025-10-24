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
       SELECT
            er.exchange_rate_id,
            er.base_rate,
            er.change_percentage,
            er.created_at,
            er.currency,
            er.rate,
            er.updated_at
        FROM (
                 SELECT
                     *,
                     ROW_NUMBER() OVER(PARTITION BY currency ORDER BY created_at DESC) as rn
                 FROM
                     exchange_rate
             ) er
        WHERE
            er.rn = 1
    """,
        nativeQuery = true,
    )
    fun findLatestExchangeRates(): List<ExchangeRate>
}
