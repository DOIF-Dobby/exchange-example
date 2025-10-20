package com.switchwon.exchange.example.system.web.exception

import org.springframework.http.HttpStatus

class UnauthorizedException(
    code: String = "UNAUTHORIZED",
    message: String = "로그인이 필요한 서비스입니다.",
) : ApiException(
    httpStatus = HttpStatus.UNAUTHORIZED,
    code = code,
    message = message,
)
