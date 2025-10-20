package com.switchwon.exchange.example.features.exchangerate.api

import com.switchwon.exchange.example.features.exchangerate.application.dto.ExchangeRateResponse
import com.switchwon.exchange.example.features.exchangerate.application.service.ExchangeRateService
import com.switchwon.exchange.example.system.web.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Exchange Rate API", description = "환율 관련 API")
@RestController
class ExchangeRateController(
    private val exchangeRateService: ExchangeRateService
) {

    /**
     * 최신 환율 조회
     */
    @Operation(summary = "최신 환율 조회", description = "최신 환율을 조회합니다.")
    @GetMapping("/exchange-rates/latest")
    fun getLatestExchangeRates(): ApiResponse<List<ExchangeRateResponse>> {
        val rates = exchangeRateService.findLatestExchangeRateResponses()
        return ApiResponse.ok(rates)
    }
}
