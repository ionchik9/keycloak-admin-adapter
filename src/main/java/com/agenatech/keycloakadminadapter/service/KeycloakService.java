package com.agenatech.keycloakadminadapter.service;

import com.agenatech.keycloakadminadapter.model.payload.UserAccount;
import com.agenatech.keycloakadminadapter.model.payload.request.SignupRequest;
import com.agenatech.keycloakadminadapter.model.payload.request.keycloak.KeycloakSignupRequest;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

public interface KeycloakService {
    Mono<URI> signup(SignupRequest request);
    Mono<URI> signup(KeycloakSignupRequest request);
    Mono<Void> deleteAccount(UUID accountId);
    Mono<UserAccount> getAccount(UUID accountId);
}
