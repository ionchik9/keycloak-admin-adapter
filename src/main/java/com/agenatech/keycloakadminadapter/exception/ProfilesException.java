package com.agenatech.keycloakadminadapter.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ProfilesException extends RuntimeException {
    private final String profileId;
    private final HttpStatus httpStatus;

    public ProfilesException(String message, String profileId, HttpStatus httpStatus) {
        super("Error during profile request, profileId = " + profileId + "\n original message: " + message);
        this.httpStatus = httpStatus;
        this.profileId = profileId;
    }

    public ProfilesException(String message, Throwable cause, String profileId, HttpStatus httpStatus) {
        super("Error during profile creation, profileId = " + profileId + "\n original message: " + message, cause);
        this.httpStatus = httpStatus;
        this.profileId = profileId;
    }
}
