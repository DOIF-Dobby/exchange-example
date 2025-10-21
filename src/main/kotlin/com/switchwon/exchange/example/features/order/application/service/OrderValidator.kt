package com.switchwon.exchange.example.features.order.application.service

import com.switchwon.exchange.example.shared.domain.Currency
import com.switchwon.exchange.example.system.web.exception.BadRequestException
import org.springframework.stereotype.Component

@Component
class OrderValidator {

    /**
     * 매수/매도 통화가 원화(KRW)를 포함하고 있는지 검증합니다.
     * - 매수인 경우: fromCurrency는 KRW, toCurrency는 외화
     * - 매도인 경우: fromCurrency는 외화, toCurrency는 KRW
     */
    fun validateCurrencyPair(fromCurrency: Currency, toCurrency: Currency) {
        if (!fromCurrency.isKrw() && !toCurrency.isKrw()) {
            throw BadRequestException(
                code = "INVALID_CURRENCY_PAIR",
                message = "매수/매도 통화는 원화가 포함되어야 합니다. 입력된 통화: from=$fromCurrency, to=$toCurrency"
            )
        }

        if (fromCurrency == toCurrency) {
            throw BadRequestException(
                code = "SAME_CURRENCY_PAIR",
                message = "매수/매도 통화는 서로 다른 통화여야 합니다. 입력된 통화: from=$fromCurrency, to=$toCurrency"
            )
        }
    }

}
