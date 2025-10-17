package com.switchwon.exchange.example.shared.domain

import java.math.RoundingMode
import java.text.DecimalFormat

enum class Currency(
    val scale: Int,
    val roundingMode: RoundingMode,
    val decimalFormat: DecimalFormat,
    val unit: Int = 1
) {
    KRW(scale = 0, roundingMode = RoundingMode.DOWN, decimalFormat = DecimalFormat("#,###")),
    USD(scale = 2, roundingMode = RoundingMode.HALF_UP, decimalFormat = DecimalFormat("#,##0.00")),
    JPY(scale = 2, roundingMode = RoundingMode.HALF_UP, decimalFormat = DecimalFormat("#,###"), unit = 100),
    ;


    fun isKrw(): Boolean {
        return this == KRW
    }
}
