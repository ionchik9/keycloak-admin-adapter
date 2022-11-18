package com.agenatech.keycloakadminadapter.service;

import com.agenatech.keycloakadminadapter.model.payload.UserAccount;
import com.agenatech.keycloakadminadapter.model.payload.request.SignupRequest;
import com.agenatech.keycloakadminadapter.model.payload.request.keycloak.KeycloakSignupRequest;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

public interface KeycloakService {
    <T extends SignupRequest> Mono<URI> signup(T request);
    Mono<URI> signup(KeycloakSignupRequest request);
    Mono<ResponseEntity> deleteAccount(UUID accountId);
    Mono<UserAccount> getAccount(UUID accountId);
}
