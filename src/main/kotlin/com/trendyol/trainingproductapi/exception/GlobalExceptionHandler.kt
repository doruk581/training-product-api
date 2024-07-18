package com.trendyol.trainingproductapi.exception

import com.trendyol.trainingproductapi.model.exception.ErrorDetailResponse
import com.trendyol.trainingproductapi.model.exception.ErrorResponse
import com.trendyol.trainingproductapi.model.exception.ProductNotFoundException
import com.trendyol.trainingproductapi.model.exception.ReviewNotFoundException
import org.apache.commons.lang3.exception.ExceptionUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ServerWebInputException
import org.springframework.http.converter.HttpMessageNotReadableException

@RestControllerAdvice
class GlobalControllerExceptionHandler() {

    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(ProductNotFoundException::class)
    fun handleContentNotFoundException(exception: ProductNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(HttpStatus.NOT_FOUND.value().toString(), exception.message)
        logger.error("ProductNotFoundException : $exception")
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ReviewNotFoundException::class)
    fun handleContentNotFoundException(exception: ReviewNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(HttpStatus.NOT_FOUND.value().toString(), exception.message)
        logger.error("ReviewNotFoundException : $exception")
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(exception = "Validation Error")
        exception.bindingResult.fieldErrors.forEach { fieldError ->
            errorResponse.addError(ErrorDetailResponse(fieldError.field, fieldError.defaultMessage ?: "Invalid value"))
        }
        logger.error("Field validation failed. Caused By:{}", exception)
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(BindException::class)
    fun handleBindException(exception: BindException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(exception = "Validation Error")
        exception.bindingResult.fieldErrors.forEach { fieldError ->
            errorResponse.addError(ErrorDetailResponse(fieldError.field, fieldError.defaultMessage ?: "Invalid value"))
        }
        logger.error("Field validation failed. Caused By:{}", exception)
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(exception: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(exception = "IllegalArgumentException")
        errorResponse.addError(ErrorDetailResponse(message = exception.message!!))
        logger.error("IllegalArgumentException Caused by: {}", exception)
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ServerWebInputException::class)
    fun handleServerWebInputException(exception: ServerWebInputException): ResponseEntity<ErrorResponse> {
        var message = exception.message
        if (exception.cause is TypeMismatchException && exception.cause?.cause is java.lang.NumberFormatException) {
            val typeMismatchException = exception.cause as TypeMismatchException
            message = typeMismatchException.cause?.message + " numeric value is expected"
        }
        val errorResponse = ErrorResponse(exception = "ServerWebInputException")
        errorResponse.addError(ErrorDetailResponse(message = message))
        logger.error("ServerWebInputException. Caused by: ", exception)
        logger.error("ServerWebInputException. Details: ${exception.mostSpecificCause.message} ${exception.rootCause?.message} ${exception.reason} ${exception.cause?.message} $exception")
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(exception: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(exception = "HttpMessageNotReadableException")
        errorResponse.addError(ErrorDetailResponse(message = "Invalid JSON input: ${exception.message}"))
        logger.error("HttpMessageNotReadableException. Caused by: ", exception)
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [java.lang.Exception::class, RuntimeException::class, Exception::class])
    fun handleGenericException(exception: Exception): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(exception = "Exception")
        errorResponse.addError(ErrorDetailResponse(message = exception.message ?: "Unexpected"))
        logger.error(
            "Unexpected exception occurred. Caused By:{}, stackTrace: {}",
            errorResponse,
            ExceptionUtils.getStackTrace(exception)
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
