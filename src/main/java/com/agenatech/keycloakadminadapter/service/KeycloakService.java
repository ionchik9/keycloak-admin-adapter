package com.agenatech.keycloakadminadapter.service;

import com.agenatech.keycloakadminadapter.model.KeycloakRequiredAction;
import com.agenatech.keycloakadminadapter.model.payload.request.SignupRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface KeycloakService {
    ResponseEntity signup(SignupRequest request);
    ResponseEntity emailRequiredAction(String userId, List<KeycloakRequiredAction> requiredActions);
}
