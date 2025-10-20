package com.switchwon.exchange.example.features.order.api

import com.switchwon.exchange.example.features.order.application.dto.OrderQuoteRequest
import com.switchwon.exchange.example.features.order.application.dto.OrderQuoteResponse
import com.switchwon.exchange.example.features.order.application.dto.OrderRequest
import com.switchwon.exchange.example.features.order.application.dto.OrderResponse
import com.switchwon.exchange.example.features.order.application.service.OrderFindService
import com.switchwon.exchange.example.features.order.application.service.OrderQuoteService
import com.switchwon.exchange.example.features.order.application.service.OrderService
import com.switchwon.exchange.example.system.security.AuthenticationUtils
import com.switchwon.exchange.example.system.web.ApiResponse
import com.switchwon.exchange.example.system.web.UnitApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Order API", description = "환전 주문 관련 API")
@RestController
class OrderController(
    private val orderService: OrderService,
    private val orderFindService: OrderFindService,
    private val orderQuoteService: OrderQuoteService,
) {

    /**
     * 환전 주문 요청
     */
    @Operation(summary = "환전 주문 요청", description = "회원이 환전 주문을 요청합니다.")
    @PostMapping("/orders")
    fun order(@RequestBody @Valid request: OrderRequest): UnitApiResponse {
        orderService.order(
            memberId = AuthenticationUtils.requireCurrentMemberId(),
            request = request
        )

        return ApiResponse.ok()
    }

    /**
     * 회원의 환전 주문 내역 조회
     */
    @Operation(summary = "환전 주문 내역 조회", description = "회원의 환전 주문 내역을 조회합니다.")
    @GetMapping("/orders")
    fun getOrders(): ApiResponse<List<OrderResponse>> {
        val orders = orderFindService
            .findOrderResponsesByMemberId(AuthenticationUtils.requireCurrentMemberId())

        return ApiResponse.ok(orders)
    }

    /**
     * 환전 주문 견적 조회
     */
    @Operation(
        summary = "환전 주문 견적 조회", description = """
        환전 주문을 위한 견적을 조회합니다.
        - 외화 매수인 경우, 해당 외화를 매수하기 위해 필요한 KRW 금액을 반환합니다.
        - 외화 매도인 경우, 해당 외화를 매도했을 때 받을 수 있는 KRW 금액을 반환합니다.
    """
    )
    @GetMapping("/orders/quote")
    fun getQuote(@ModelAttribute request: OrderQuoteRequest): ApiResponse<OrderQuoteResponse> {
        val quoteResponse = orderQuoteService.getQuote(request)
        return ApiResponse.ok(quoteResponse)
    }
}
