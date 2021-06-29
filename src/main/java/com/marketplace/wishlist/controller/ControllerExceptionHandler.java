package com.marketplace.wishlist.controller;

import com.marketplace.wishlist.exception.BusinessException;
import com.marketplace.wishlist.exception.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) {
        Problem problem = Problem.builder()
                .code(String.valueOf(ex.getHttpStatus()))
                .message(ex.getMessage())
                .build();
        return handleExceptionInternal(ex, problem, new HttpHeaders(), HttpStatus.valueOf(ex.getHttpStatus()), request);
    }
}
