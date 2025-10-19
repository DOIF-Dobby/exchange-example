package com.switchwon.exchange.example.features.wallet.api

import com.switchwon.exchange.example.features.member.domain.MemberId
import com.switchwon.exchange.example.features.wallet.application.dto.WalletSummaryResponse
import com.switchwon.exchange.example.features.wallet.application.service.WalletService
import com.switchwon.exchange.example.system.web.ApiResponse
import com.switchwon.exchange.example.system.web.resolver.CurrentMemberId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WalletController(
    private val walletService: WalletService
) {

    /**
     * 회원의 지갑 조회
     * - 회원이 보유한 통화별 잔액을 조회합니다.
     */
    @GetMapping("/wallets")
    fun getWallets(@CurrentMemberId memberId: MemberId): ApiResponse<WalletSummaryResponse> {
        val walletSummary = walletService.findWalletSummary(memberId)
        return ApiResponse.ok(walletSummary)
    }
}
