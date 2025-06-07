package com.workintech.spring17challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleException(ApiException apiException) {
        ApiErrorResponse responseError = new ApiErrorResponse(apiException.getHttpStatus().value(), apiException.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(responseError, apiException.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleException(Exception exception) {
        ApiErrorResponse responseError = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }
}
