package com.agenatech.keycloakadminadapter.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@RestControllerAdvice
public class WebClientExceptionAdvice {
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<String> exceptionHandler(WebClientResponseException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());
    }
}
