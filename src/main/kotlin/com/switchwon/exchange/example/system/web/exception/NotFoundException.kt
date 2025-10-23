package com.switchwon.exchange.example.system.web.exception

import org.springframework.http.HttpStatus

class NotFoundException(
    code: String = "NOT_FOUND",
    message: String = "요청한 URL을 찾을 수 없어요.",
) : ApiException(
        httpStatus = HttpStatus.NOT_FOUND,
        code = code,
        message = message,
    )
