package com.switchwon.exchange.example.features.member.application.service

import com.switchwon.exchange.example.features.member.application.dto.MemberResponse
import com.switchwon.exchange.example.features.member.domain.Member
import com.switchwon.exchange.example.features.member.domain.MemberId
import com.switchwon.exchange.example.features.member.domain.MemberRepository
import com.switchwon.exchange.example.system.data.findByIdOrThrow
import com.switchwon.exchange.example.system.web.exception.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val createNewMemberService: CreateNewMemberService,
) {
    /**
     * 이메일로 회원을 조회하고, 없으면 새로 등록합니다.
     */
    @Transactional
    fun findOrRegister(email: String): Member {
        return memberRepository.findByEmail(email)
            ?: createNewMemberService.createNewMember(email)
    }

    /**
     * 회원 ID로 회원 정보를 조회합니다.
     * 회원이 존재하지 않으면 NotFoundException을 발생시킵니다.
     */
    @Transactional(readOnly = true)
    fun findMember(memberId: MemberId): Member {
        val member =
            memberRepository.findByIdOrThrow(memberId.value) {
                NotFoundException(code = "MEMBER_NOT_FOUND", message = "회원이 존재하지 않습니다. ID: $memberId")
            }

        return member
    }

    /**
     * 회원 ID로 회원 정보를 조회하고, MemberResponse로 변환합니다.
     * 회원이 존재하지 않으면 NotFoundException을 발생시킵니다.
     */
    @Transactional(readOnly = true)
    fun findMemberResponse(memberId: MemberId): MemberResponse {
        val member = findMember(memberId)

        return MemberResponse.from(member)
    }
}
