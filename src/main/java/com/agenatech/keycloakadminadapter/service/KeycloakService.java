package com.agenatech.keycloakadminadapter.service;

import com.agenatech.keycloakadminadapter.model.payload.request.SignupRequest;
import com.agenatech.keycloakadminadapter.model.payload.request.keycloak.KeycloakSignupRequest;
import reactor.core.publisher.Mono;

import java.net.URI;

public interface KeycloakService {
    Mono<URI> signup(SignupRequest request);
    Mono<URI> signup(KeycloakSignupRequest request);
}
