package com.switchwon.exchange.example.shared.domain

import com.switchwon.exchange.example.shared.domain.exception.InvalidAmountScaleException
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

enum class Currency(
    val roundingScale: Int = 2, // 환율 계산/표시용 소수점 자리수
    val roundingMode: RoundingMode = RoundingMode.HALF_UP,
    val allowedOrderScale: Int = 2, // 주문 금액 소수점 허용 자리수
    val unit: Int = 1,
    val decimalFormat: DecimalFormat = DecimalFormat("#,##0.00"),
) {
    KRW(
        roundingScale = 0, // 원화는 소수점으로 표시하지 않음
        roundingMode = RoundingMode.DOWN,
        allowedOrderScale = 0, // 원화는 소수점 없이 정수 단위로만 거래
        decimalFormat = DecimalFormat("#,###"),
    ),
    USD,
    JPY(
        unit = 100, // 일본 엔화는 100엔 단위로 거래
        allowedOrderScale = 0, // 일본 엔화는 소수점 없이 정수 단위로만 거래
    ),
    ;

    fun isKrw(): Boolean {
        return this == KRW
    }

    /**
     * 주문 금액의 소수점 자릿수가 이 통화의 정책(allowedOrderScale)에 맞는지 검증합니다.
     */
    fun validateOrderAmountScale(amount: BigDecimal) {
        if (amount.scale() > this.allowedOrderScale) {
            throw InvalidAmountScaleException(
                currency = this,
                expectedScale = this.allowedOrderScale,
                actualScale = amount.scale(),
            )
        }
    }
}
