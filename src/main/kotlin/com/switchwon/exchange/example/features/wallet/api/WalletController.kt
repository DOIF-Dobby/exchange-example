package com.switchwon.exchange.example.features.wallet.api

import com.switchwon.exchange.example.features.member.domain.MemberId
import com.switchwon.exchange.example.features.wallet.application.dto.WalletResponse
import com.switchwon.exchange.example.features.wallet.application.service.WalletService
import com.switchwon.exchange.example.system.web.ApiResponse
import com.switchwon.exchange.example.system.web.resolver.UserId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WalletController(
    private val walletService: WalletService
) {

    @GetMapping("/wallets")
    fun getWallets(@UserId memberId: Long): ApiResponse<List<WalletResponse>> {
        val wallets = walletService.findWalletByMemberId(MemberId(memberId))
        return ApiResponse.ok(wallets)
    }
}
