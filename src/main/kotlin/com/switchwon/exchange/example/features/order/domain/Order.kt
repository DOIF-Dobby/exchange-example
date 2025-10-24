package com.switchwon.exchange.example.features.order.domain

import com.switchwon.exchange.example.features.member.domain.MemberId
import com.switchwon.exchange.example.shared.domain.Money
import com.switchwon.exchange.example.system.data.BaseEntity
import jakarta.persistence.AttributeOverride
import jakarta.persistence.AttributeOverrides
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Table(name = "orders")
@Entity
class Order(

    @Column(nullable = false)
    val memberId: MemberId,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "currency", column = Column(name = "from_currency", nullable = false, updatable = false)),
        AttributeOverride(name = "amount", column = Column(name = "from_amount", nullable = false, updatable = false)),
    )
    val fromMoney: Money,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "currency", column = Column(name = "to_currency", nullable = false, updatable = false)),
        AttributeOverride(name = "amount", column = Column(name = "to_amount", nullable = false, updatable = false)),
    )
    val toMoney: Money,

    @Column(nullable = false, updatable = false)
    val appliedRate: BigDecimal,
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    val id: OrderId = OrderId(0L)
}
