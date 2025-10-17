package com.switchwon.exchange.example.features.order.domain

import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long> {

}
