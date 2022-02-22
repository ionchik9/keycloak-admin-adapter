package com.agenatech.keycloakadminadapter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.net.ConnectException;

@RestControllerAdvice
public class WebClientExceptionAdvice {
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<String> exceptionHandler(WebClientResponseException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<String> exceptionHandler(ConnectException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
    }
}


