package com.agenatech.keycloakadminadapter.service;

import com.agenatech.keycloakadminadapter.model.KeycloakRequiredAction;
import com.agenatech.keycloakadminadapter.model.payload.request.SignupRequest;
import com.agenatech.keycloakadminadapter.model.payload.request.keycloak.KeycloakSignupRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface KeycloakService {
    ResponseEntity signup(SignupRequest request);
    ResponseEntity signup(KeycloakSignupRequest request);
    ResponseEntity emailRequiredAction(String userId, List<KeycloakRequiredAction> requiredActions);
}
