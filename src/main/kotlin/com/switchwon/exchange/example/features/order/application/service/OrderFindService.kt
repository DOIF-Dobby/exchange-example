package com.switchwon.exchange.example.features.order.application.service

import com.switchwon.exchange.example.features.member.domain.MemberId
import com.switchwon.exchange.example.features.order.application.dto.OrderResponse
import com.switchwon.exchange.example.features.order.domain.OrderRepository
import org.springframework.stereotype.Service

@Service
class OrderFindService(
    private val orderRepository: OrderRepository,
) {

    /**
     * 회원의 주문 내역을 조회합니다.
     * - 주문은 최신순으로 정렬되어 반환됩니다.
     */
    fun findOrderResponsesByMemberId(memberId: MemberId): List<OrderResponse> =
        orderRepository
            .findAllByMemberId(memberId.value)
            .map { OrderResponse.from(it) }
}
