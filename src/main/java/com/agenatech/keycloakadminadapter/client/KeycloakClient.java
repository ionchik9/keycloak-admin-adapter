package com.agenatech.keycloakadminadapter.client;


import com.agenatech.keycloakadminadapter.model.payload.request.keycloak.KeycloakAdminTokenRequest;
import com.agenatech.keycloakadminadapter.model.payload.request.keycloak.KeycloakSignupRequest;
import com.agenatech.keycloakadminadapter.model.payload.response.AuthResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "keycloak", url = "${auth-server.url}")
@Service
public interface KeycloakClient {
    @PostMapping(value = "${auth-server.cli-token-uri}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @Headers("Content-Type: application/x-www-form-urlencoded")
    AuthResponse getCliToken(KeycloakAdminTokenRequest cliRequest);

    @PostMapping(value = "${auth-server.users-uri}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity registerUser(KeycloakSignupRequest signupRequest, @RequestHeader("Authorization") String adminToken);
}

