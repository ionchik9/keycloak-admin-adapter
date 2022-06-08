package com.agenatech.keycloakadminadapter.service.impl;

import com.agenatech.keycloakadminadapter.client.KeycloakClient;
import com.agenatech.keycloakadminadapter.model.payload.UserAccount;
import com.agenatech.keycloakadminadapter.model.payload.request.SignupRequest;
import com.agenatech.keycloakadminadapter.model.payload.request.keycloak.KeycloakAdminTokenRequest;
import com.agenatech.keycloakadminadapter.model.payload.request.keycloak.KeycloakSignupRequest;
import com.agenatech.keycloakadminadapter.model.payload.response.AuthResponse;
import com.agenatech.keycloakadminadapter.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {
    private final KeycloakClient keycloackClient;

    private static final String BEARER = "Bearer ";


    @Override
    public Mono<URI> signup(SignupRequest request) {
        KeycloakSignupRequest signupRequest =
                KeycloakSignupRequest
                        .builder()
                        .email(request.getEmail())
                        .credentials(request.getCredentials())
                        .enabled(Optional.ofNullable(request.getEnabled()).orElse(true))
                        .build();
        return adminLogin()
                .flatMap(authResponse -> keycloackClient.createAccount(signupRequest, BEARER + authResponse.accessToken()));
    }

    @Override
    public Mono<URI> signup(KeycloakSignupRequest request) {
        return adminLogin()
                .flatMap(authResponse -> keycloackClient.createAccount(request, BEARER + authResponse.accessToken()));
    }

    @Override
    public Mono<Void> deleteAccount(UUID accountId) {
        return adminLogin()
                .flatMap(authResponse -> keycloackClient.deleteAccount(accountId,  BEARER + authResponse.accessToken()));
    }

    @Override
    public Mono<UserAccount> getAccount(UUID accountId) {
        return adminLogin()
                .flatMap(authResponse -> keycloackClient.getAccount(accountId,  BEARER + authResponse.accessToken()));
    }


    private Mono<AuthResponse> adminLogin() {
        KeycloakAdminTokenRequest adminTokenRequest =
                KeycloakAdminTokenRequest
                        .builder()
                        .clientId("admin-cli")
                        .clientSecret(keycloackClient.getAdminSecret())
                        .grantType("client_credentials")
                        .build();
        return keycloackClient.getCliToken(adminTokenRequest);
    }

}
