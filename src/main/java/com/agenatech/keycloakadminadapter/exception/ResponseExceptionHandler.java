package com.agenatech.keycloakadminadapter.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class ResponseExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(FeignException.BadRequest.class)
    public String handleBadRequestException(FeignException e, HttpServletResponse response) {
        response.setStatus(e.status());
        return e.contentUTF8();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(FeignException.Conflict.class)
    public String handleFeignConflictException(FeignException e, HttpServletResponse response) {
        response.setStatus(e.status());
        return e.contentUTF8();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(FeignException.Unauthorized.class)
    public String handleFeignUnauthorizedException(FeignException e, HttpServletResponse response) {
        response.setStatus(e.status());
        return e.contentUTF8();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ProfilesException.class)
    public String handleProfilesException(ProfilesException e, HttpServletResponse response) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return e.getMessage();
    }
}
