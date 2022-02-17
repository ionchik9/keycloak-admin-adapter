package com.agenatech.keycloakadminadapter.service;

import com.agenatech.keycloakadminadapter.model.payload.request.SignupRequest;
import com.agenatech.keycloakadminadapter.model.payload.request.keycloak.KeycloakSignupRequest;
import org.springframework.http.ResponseEntity;

public interface KeycloakService {
    ResponseEntity signup(SignupRequest request);
    ResponseEntity signup(KeycloakSignupRequest request);
}
