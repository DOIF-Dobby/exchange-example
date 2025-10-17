package com.switchwon.exchange.example.features.member.api

import com.switchwon.exchange.example.features.member.application.dto.MemberResponse
import com.switchwon.exchange.example.features.member.application.service.MemberService
import com.switchwon.exchange.example.features.member.domain.MemberId
import com.switchwon.exchange.example.system.web.ApiResponse
import com.switchwon.exchange.example.system.web.resolver.UserId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberController(
    private val memberService: MemberService
) {

    @GetMapping("/members")
    fun getMembers(@UserId memberId: Long): ApiResponse<MemberResponse> {
        val member = memberService.findMemberResponse(MemberId(memberId))
        return ApiResponse.ok(member)
    }

}
