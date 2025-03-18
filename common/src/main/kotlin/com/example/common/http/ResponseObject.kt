package com.example.common.http

data class ResponseObject<T>(
    val code: Int,
    val message: String,
    val result: T?
) {
    companion object {
        fun <T> of(result: T?): ResponseObject<T> = ResponseObject(
            ResponseCode.SUCCESS.code,
            ResponseCode.SUCCESS.message,
            result
        )

        fun <T> error(code: Int, message: String): ResponseObject<T> = ResponseObject(
            code,
            message,
            null
        )
    }
}

