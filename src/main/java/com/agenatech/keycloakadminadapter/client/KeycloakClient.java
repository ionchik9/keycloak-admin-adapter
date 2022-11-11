package com.agenatech.keycloakadminadapter.client;


import com.agenatech.keycloakadminadapter.config.KeycloakConfig;
import com.agenatech.keycloakadminadapter.model.payload.UserAccount;
import com.agenatech.keycloakadminadapter.model.payload.request.keycloak.KeycloakAdminTokenRequest;
import com.agenatech.keycloakadminadapter.model.payload.request.keycloak.KeycloakSignupRequest;
import com.agenatech.keycloakadminadapter.model.payload.response.AuthResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.logging.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.Map;
import java.util.UUID;


@Service
public class KeycloakClient {
    private WebClient webClient;

    private final KeycloakConfig keycloakConfig;
    private final ObjectMapper objectMapper;

    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    @Autowired
    public KeycloakClient(KeycloakConfig clientConfig, ObjectMapper objectMapper) {
        this.keycloakConfig = clientConfig;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init(){
        webClient = WebClient.builder()
                .baseUrl(keycloakConfig.url())
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL)
                ))
                .build();
    }

    public Mono<AuthResponse> getCliToken(KeycloakAdminTokenRequest adminTokenRequest) {
        return webClient
                .post()
                .uri(keycloakConfig.cliTokenUri())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(BodyInserters.fromFormData(convertToForm(adminTokenRequest)))
                .retrieve()
                .bodyToMono(AuthResponse.class);
    }

    public Mono<URI> createAccount(KeycloakSignupRequest signupRequest, String adminToken) {
        return webClient
                .post()
                .uri(keycloakConfig.usersUri())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION_HEADER_NAME, adminToken)
                .body(BodyInserters.fromValue(signupRequest))
                .retrieve()
                .toBodilessEntity()
                .map(clientResponse -> clientResponse.getHeaders().getLocation());
    }

    public Mono<ResponseEntity> deleteAccount(UUID userId, String adminToken) {
        return webClient
                .delete()
                .uri(keycloakConfig.usersUri() + "/" + userId)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION_HEADER_NAME, adminToken)
                .retrieve()
                .bodyToMono(ResponseEntity.class);
    }


    public Mono<Void> setTherapistRole(UUID userId, String adminToken) {
        return webClient
                .post()
                .uri(keycloakConfig.usersUri() + "/" + userId)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION_HEADER_NAME, adminToken)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<UserAccount> getAccount(UUID userId, String adminToken) {
        return webClient
                .get()
                .uri(keycloakConfig.usersUri() + "/" + userId)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION_HEADER_NAME, adminToken)
                .retrieve()
                .bodyToMono(UserAccount.class);
    }


    MultiValueMap<String, String> convertToForm(Object obj) {
        var parameters = new LinkedMultiValueMap<String, String>();
        Map<String, String> maps = objectMapper.convertValue(obj, new TypeReference<>() {});
        parameters.setAll(maps);
        return parameters;
    }
}

