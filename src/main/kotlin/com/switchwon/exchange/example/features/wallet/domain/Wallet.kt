package com.switchwon.exchange.example.features.wallet.domain

import com.switchwon.exchange.example.features.member.domain.Member
import com.switchwon.exchange.example.features.wallet.domain.exception.InvalidDepositAmountException
import com.switchwon.exchange.example.features.wallet.domain.exception.InvalidWithdrawAmountException
import com.switchwon.exchange.example.features.wallet.domain.exception.WalletInsufficientBalanceException
import com.switchwon.exchange.example.shared.domain.Currency
import com.switchwon.exchange.example.shared.domain.Money
import com.switchwon.exchange.example.system.data.BaseEntity
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.math.BigDecimal

@Entity
class Wallet(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, updatable = false)
    val member: Member,

    currency: Currency,
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_id")
    val id: WalletId = WalletId(0L)

    @Embedded
    @AttributeOverride(name = "amount", column = Column(name = "balance_amount", nullable = false))
    var balance: Money = Money(currency, BigDecimal.ZERO)

    /**
     * 지갑에 금액을 입금합니다. 입금액은 반드시 0 이상이어야 합니다.
     */
    fun deposit(amount: Money) {
        if (amount.isNegative()) {
            throw InvalidDepositAmountException()
        }
        this.balance += amount
    }

    /**
     * 지갑에서 금액을 출금합니다. 출금액은 반드시 0 이상이어야 하며, 잔액이 충분해야 합니다.
     */
    fun withdraw(amount: Money) {
        if (amount.isNegative()) {
            throw InvalidWithdrawAmountException()
        }

        val afterBalance = this.balance - amount
        if (afterBalance.isNegative()) {
            throw WalletInsufficientBalanceException()
        }

        this.balance = afterBalance
    }
}
