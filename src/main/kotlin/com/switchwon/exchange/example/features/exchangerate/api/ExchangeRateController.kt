package com.switchwon.exchange.example.features.exchangerate.api

import com.switchwon.exchange.example.features.exchangerate.application.dto.ExchangeRateResponse
import com.switchwon.exchange.example.features.exchangerate.application.service.ExchangeRateService
import com.switchwon.exchange.example.system.web.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ExchangeRateController(
    private val exchangeRateService: ExchangeRateService
) {

    @GetMapping("/exchange-rates/latest")
    fun getLatestExchangeRates(): ApiResponse<List<ExchangeRateResponse>> {
        val rates = exchangeRateService.findLatestExchangeRateResponses()
        return ApiResponse.ok(rates)
    }
}
