package com.switchwon.exchange.example.features.member.application.service

import com.switchwon.exchange.example.features.member.domain.Member
import com.switchwon.exchange.example.features.member.domain.MemberRepository
import com.switchwon.exchange.example.features.wallet.application.service.InitializeWalletService
import org.springframework.stereotype.Service

@Service
class CreateNewMemberService(
    private val memberRepository: MemberRepository,
    private val initializeWalletService: InitializeWalletService,
) {

    /**
     * 새 회원을 등록하고, 지갑을 초기화합니다.
     * - 이메일로 회원을 조회하고, 없으면 새로 등록합니다.
     * - 회원의 지갑을 초기화합니다.
     */
    fun createNewMember(email: String): Member {
        // 1. 비즈니스 규칙 검증: 이미 존재하는 회원인지 확인
        if (memberRepository.findByEmail(email) != null) {
            throw IllegalArgumentException("이미 가입된 이메일입니다.")
        }

        // 2. 신규 회원 생성
        val newMember = memberRepository.save(Member(email = email))

        // 3. 지갑 초기화
        initializeWalletService.initializeWallet(newMember)

        // 4. 생성된 회원 반환
        return newMember
    }
}
