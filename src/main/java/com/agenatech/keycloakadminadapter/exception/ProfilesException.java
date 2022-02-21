package com.agenatech.keycloakadminadapter.exception;

public class ProfilesException extends RuntimeException {
    public ProfilesException(String message) {
        super(message);
    }

    public ProfilesException(String message, Throwable cause) {
        super(message, cause);
    }
}
