package com.switchwon.exchange.example.features.wallet.application.service

import com.switchwon.exchange.example.features.member.application.service.MemberService
import com.switchwon.exchange.example.features.member.domain.MemberId
import com.switchwon.exchange.example.features.wallet.application.dto.WalletResponse
import com.switchwon.exchange.example.features.wallet.domain.WalletRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WalletService(
    private val walletRepository: WalletRepository,
    private val memberService: MemberService
) {

    /**
     * 회원의 지갑을 조회합니다.
     * - 회원 ID로 지갑을 조회합니다.
     */
    @Transactional(readOnly = true)
    fun findWalletByMemberId(memberId: MemberId): List<WalletResponse> {
        val member = memberService.findMember(memberId)

        return walletRepository.findByMember(member)
            .map { WalletResponse.from(it) }
    }


}
