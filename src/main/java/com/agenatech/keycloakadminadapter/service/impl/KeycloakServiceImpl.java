package com.agenatech.keycloakadminadapter.service.impl;

import com.agenatech.keycloakadminadapter.client.KeycloakClient;
import com.agenatech.keycloakadminadapter.config.KeycloakConfig;
import com.agenatech.keycloakadminadapter.model.payload.UserAccount;
import com.agenatech.keycloakadminadapter.model.payload.request.SignupRequest;
import com.agenatech.keycloakadminadapter.model.payload.request.keycloak.KeycloakAdminTokenRequest;
import com.agenatech.keycloakadminadapter.model.payload.request.keycloak.KeycloakSignupRequest;
import com.agenatech.keycloakadminadapter.model.payload.response.AuthResponse;
import com.agenatech.keycloakadminadapter.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {
    private final KeycloakClient keycloackClient;
    private final KeycloakConfig keycloackConfig;

    private static final String BEARER = "Bearer ";


    @Override
    @SneakyThrows
    public <T extends SignupRequest> Mono<URI> signup(T request) {
        var signupRequest = new KeycloakSignupRequest();
        BeanUtils.copyProperties(signupRequest, request);
        return adminLogin()
                .flatMap(authResponse -> keycloackClient.createAccount(signupRequest, BEARER + authResponse.accessToken()));
    }

    @Override
    public Mono<URI> signup(KeycloakSignupRequest request) {
        return adminLogin()
                .flatMap(authResponse -> keycloackClient.createAccount(request, BEARER + authResponse.accessToken()));
    }

    @Override
    public Mono<ResponseEntity> deleteAccount(UUID accountId) {
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
                        .clientSecret(keycloackConfig.adminSecret())
                        .grantType("client_credentials")
                        .build();
        return keycloackClient.getCliToken(adminTokenRequest);
    }

}
