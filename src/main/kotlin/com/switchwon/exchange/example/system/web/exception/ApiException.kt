package com.switchwon.exchange.example.system.web.exception

import org.springframework.http.HttpStatus

open class ApiException(
    val httpStatus: HttpStatus,
    val code: String,
    message: String = httpStatus.reasonPhrase,
) : RuntimeException(message)
