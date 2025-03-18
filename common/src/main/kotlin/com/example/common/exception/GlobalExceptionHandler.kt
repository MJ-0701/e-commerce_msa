package com.example.common.exception

import com.example.common.http.ResponseCode
import com.example.common.http.ResponseObject
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    // 모든 예외 처리
    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception): ResponseEntity<ResponseObject<Nothing>> {
        logger.error("Unhandled exception occurred", ex)
        val response = ResponseObject.error<Nothing>(
            code = ResponseCode.INTERNAL_SERVER_ERROR.code,
            message = ResponseCode.INTERNAL_SERVER_ERROR.message
        )
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    // 유효성 검증 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<ResponseObject<Nothing>> {
        val errors = ex.bindingResult.fieldErrors.map { fieldError: FieldError ->
            "${fieldError.field}: ${fieldError.defaultMessage}"
        }.joinToString(", ")

        val response = ResponseObject.error<Nothing>(
            code = ResponseCode.BAD_REQUEST.code,
            message = "Validation failed: $errors"
        )
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    // 특정 커스텀 예외 처리 (예: ResourceNotFoundException)
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFound(ex: ResourceNotFoundException): ResponseEntity<ResponseObject<Nothing>> {
        val response = ResponseObject.error<Nothing>(
            code = ResponseCode.NOT_FOUND.code,
            message = ex.message ?: ResponseCode.NOT_FOUND.message
        )
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }
}