package com.switchwon.exchange.example.features.wallet.domain

import com.switchwon.exchange.example.features.member.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface WalletRepository : JpaRepository<Wallet, Long> {

    fun findByMember(member: Member): List<Wallet>

}
