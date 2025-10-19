package com.switchwon.exchange.example.features.order.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface OrderRepository : JpaRepository<Order, Long> {

    @Query(
        """
        select o
        from Order o
        where o.memberId = :memberId
        order by o.id desc
    """
    )
    fun findAllByMemberId(memberId: Long): List<Order>
}
