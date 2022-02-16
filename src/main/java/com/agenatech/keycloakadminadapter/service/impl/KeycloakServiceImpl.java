package com.agenatech.keycloakadminadapter.service.impl;

import com.agenatech.keycloakadminadapter.client.KeycloakClient;
import com.agenatech.keycloakadminadapter.config.KeycloakConfig;
import com.agenatech.keycloakadminadapter.model.KeycloakRequiredAction;
import com.agenatech.keycloakadminadapter.model.payload.KeycloakCredentials;
import com.agenatech.keycloakadminadapter.model.payload.request.SignupRequest;
import com.agenatech.keycloakadminadapter.model.payload.request.keycloak.KeycloakAdminTokenRequest;
import com.agenatech.keycloakadminadapter.model.payload.request.keycloak.KeycloakSignupRequest;
import com.agenatech.keycloakadminadapter.model.payload.response.AuthResponse;
import com.agenatech.keycloakadminadapter.service.KeycloakService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class KeycloakServiceImpl implements KeycloakService {
    @Autowired
    private KeycloakClient keycloackClient;
    @Autowired
    private KeycloakConfig keycloackConfig;



    @Override
    public ResponseEntity signup(SignupRequest request) {
        String adminToken = adminLogin().getBody().getAccess_token();
        KeycloakCredentials credentials =
                KeycloakCredentials.builder()
                        .value(request.getPassword())
                        .temporary(Optional.ofNullable(request.getIsTemporaryPassword()).orElse(false))
                        .type("password")
                        .build();

        KeycloakSignupRequest signupRequest =
                KeycloakSignupRequest
                        .builder()
                        .email(request.getEmail())
                        .credentials(Arrays.asList(credentials))
                        .enabled(Optional.ofNullable(request.getEnabled()).orElse(true))
                        .build();
        return keycloackClient.registerUser(signupRequest, "Bearer " + adminToken);
    }

    @Override
    public ResponseEntity emailRequiredAction(String userId, List<KeycloakRequiredAction> requiredActions) {
        return keycloackClient.emailAction(userId, requiredActions, "Bearer " + adminLogin().getBody().getAccess_token());
    }

    private ResponseEntity<AuthResponse> adminLogin() {
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
