package com.switchwon.exchange.example.features.member.api

import com.switchwon.exchange.example.features.member.application.dto.MemberResponse
import com.switchwon.exchange.example.features.member.application.service.MemberService
import com.switchwon.exchange.example.features.member.domain.MemberId
import com.switchwon.exchange.example.system.web.ApiResponse
import com.switchwon.exchange.example.system.web.resolver.CurrentMemberId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController(
    private val memberService: MemberService
) {

    /**
     * 회원 정보 조회
     */
    @GetMapping("/members")
    fun getMembers(@CurrentMemberId memberId: MemberId): ApiResponse<MemberResponse> {
        val member = memberService.findMemberResponse(memberId)
        return ApiResponse.ok(member)
    }

}
