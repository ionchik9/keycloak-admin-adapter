package com.agenatech.keycloakadminadapter.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class ProfilesException extends RuntimeException {
    private String profileId;
    private HttpStatus httpStatus;

    public ProfilesException(String message, String profileId, HttpStatus httpStatus) {
        super("Error during profile creation, profileId = " + profileId + "\n original message: " + message);
        this.httpStatus = httpStatus;

    }

    public ProfilesException(String message, Throwable cause, String profileId, HttpStatus httpStatus) {
        super("Error during profile creation, profileId = " + profileId + "\n original message: " + message, cause);
        this.httpStatus = httpStatus;
    }
}
