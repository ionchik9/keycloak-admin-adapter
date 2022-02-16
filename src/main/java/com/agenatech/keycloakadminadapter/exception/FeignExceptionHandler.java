package com.agenatech.keycloakadminadapter.exception;

import feign.FeignException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class FeignExceptionHandler {
    @ExceptionHandler(FeignException.BadRequest.class)
    public String handleBadRequestException(FeignException e, HttpServletResponse response) {
        response.setStatus(e.status());
        return e.contentUTF8();
    }

    @ExceptionHandler(FeignException.Conflict.class)
    public String handleFeignConflictException(FeignException e, HttpServletResponse response) {
        response.setStatus(e.status());
        return e.contentUTF8();
    }

    @ExceptionHandler(FeignException.Unauthorized.class)
    public String handleFeignUnauthorizedException(FeignException e, HttpServletResponse response) {
        response.setStatus(e.status());
        return e.contentUTF8();
    }
}
