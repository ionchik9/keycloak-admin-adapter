package com.agenatech.keycloakadminadapter.service.impl;

import com.agenatech.keycloakadminadapter.client.KeycloakClient;
import com.agenatech.keycloakadminadapter.config.KeycloakConfig;
import com.agenatech.keycloakadminadapter.model.payload.request.SignupRequest;
import com.agenatech.keycloakadminadapter.model.payload.request.keycloak.KeycloakAdminTokenRequest;
import com.agenatech.keycloakadminadapter.model.payload.request.keycloak.KeycloakSignupRequest;
import com.agenatech.keycloakadminadapter.model.payload.response.AuthResponse;
import com.agenatech.keycloakadminadapter.service.KeycloakService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Optional;

@Service
@Slf4j
public class KeycloakServiceImpl implements KeycloakService {
    private final KeycloakClient keycloackClient;
    private final KeycloakConfig keycloackConfig;

    @Autowired
    public KeycloakServiceImpl(KeycloakClient keycloackClient, KeycloakConfig keycloackConfig) {
        this.keycloackClient = keycloackClient;
        this.keycloackConfig = keycloackConfig;
    }

    @Override
    public Mono<URI> signup(SignupRequest request) {
        KeycloakSignupRequest signupRequest =
                KeycloakSignupRequest
                        .builder()
                        .username(request.getEmail())
                        .credentials(request.getCredentials())
                        .enabled(Optional.ofNullable(request.getEnabled()).orElse(true))
                        .build();
        return adminLogin()
                .flatMap(authResponse -> keycloackClient.createAccount(signupRequest, "Bearer " + authResponse.getAccess_token()));
    }

    @Override
    public Mono<URI> signup(KeycloakSignupRequest request) {
        return adminLogin()
                .flatMap(authResponse -> keycloackClient.createAccount(request, "Bearer " + authResponse.getAccess_token()));
    }


    private Mono<AuthResponse> adminLogin() {
        KeycloakAdminTokenRequest adminTokenRequest =
                KeycloakAdminTokenRequest
                        .builder()
                        .client_id("admin-cli")
                        .client_secret(keycloackConfig.getAdminSecret())
                        .grant_type("client_credentials")
                        .build();
        return keycloackClient.getCliToken(adminTokenRequest);
    }

}
