package com.agenatech.keycloakadminadapter.exception;

import org.springframework.http.HttpStatus;

public record ErrorDto (
        String message,
        HttpStatus status,
        String errorCode) {
}
