package com.switchwon.exchange.example.features.wallet.api

import com.switchwon.exchange.example.features.wallet.application.dto.WalletSummaryResponse
import com.switchwon.exchange.example.features.wallet.application.service.WalletService
import com.switchwon.exchange.example.system.security.AuthenticationUtils
import com.switchwon.exchange.example.system.web.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Wallet API", description = "지갑 관련 API")
@RestController
class WalletController(
    private val walletService: WalletService,
) {

    /**
     * 회원의 지갑 조회
     * - 회원이 보유한 통화별 잔액을 조회합니다.
     */
    @Operation(
        summary = "지갑 조회",
        description = "회원의 지갑을 조회합니다. 회원이 보유한 통화별 잔액을 반환합니다.",
    )
    @GetMapping("/wallets")
    fun getWallets(): ApiResponse<WalletSummaryResponse> {
        val walletSummary = walletService
            .findWalletSummary(AuthenticationUtils.requireCurrentMemberId())

        return ApiResponse.ok(walletSummary)
    }
}
