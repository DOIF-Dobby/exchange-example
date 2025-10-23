package com.switchwon.exchange.example.system.web.exception

import org.springframework.http.HttpStatus

class BadRequestException(
    code: String = "BAD_REQUEST",
    message: String = "잘못된 요청입니다.",
) : ApiException(
        httpStatus = HttpStatus.BAD_REQUEST,
        code = code,
        message = message,
    )
