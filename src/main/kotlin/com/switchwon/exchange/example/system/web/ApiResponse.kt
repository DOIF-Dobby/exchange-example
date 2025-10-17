package com.switchwon.exchange.example.system.web

data class ApiResponse<T>(
    val code: String,
    val message: String,
    val data: T?
) {
    companion object {

        fun ok(): ApiResponse<Unit> {
            return ApiResponse(
                code = "OK",
                message = "정상적으로 처리되었습니다.",
                data = Unit
            )
        }

        fun <T> ok(data: T? = null): ApiResponse<T> {
            return ApiResponse(
                code = "OK",
                message = "정상적으로 처리되었습니다.",
                data = data
            )
        }

        fun fail(code: String, message: String = "처리 중 오류가 발생했습니다."): ApiResponse<Unit> {
            return ApiResponse(
                code = code,
                message = message,
                data = Unit
            )
        }

        fun <T> fail(message: String = "처리 중 오류가 발생했습니다.", data: T? = null): ApiResponse<T> {
            return ApiResponse(
                code = "FAIL",
                message = message,
                data = data
            )
        }
    }
}

typealias UnitApiResponse = ApiResponse<Unit>
