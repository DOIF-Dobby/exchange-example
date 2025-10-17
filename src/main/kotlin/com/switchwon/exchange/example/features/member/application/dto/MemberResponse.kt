package com.switchwon.exchange.example.features.member.application.dto

import com.switchwon.exchange.example.features.member.domain.Member
import java.time.LocalDateTime

data class MemberResponse(
    val id: Long,
    val email: String,
    val joinedAt: LocalDateTime,
) {
    companion object {
        fun from(member: Member) = member.run {
            MemberResponse(
                id = id.value,
                email = email,
                joinedAt = createdAt!!,
            )
        }
    }
}
