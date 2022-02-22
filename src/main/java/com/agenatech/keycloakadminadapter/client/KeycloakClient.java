package com.agenatech.keycloakadminadapter.client;


import com.agenatech.keycloakadminadapter.config.KeycloakConfig;
import com.agenatech.keycloakadminadapter.model.payload.request.keycloak.KeycloakAdminTokenRequest;
import com.agenatech.keycloakadminadapter.model.payload.request.keycloak.KeycloakSignupRequest;
import com.agenatech.keycloakadminadapter.model.payload.response.AuthResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.logging.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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


@Service
public class KeycloakClient {

//    @PostMapping(value = "${auth-server.cli-token-uri}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//    @Headers("Content-Type: application/x-www-form-urlencoded")
//    AuthResponse getCliToken(KeycloakAdminTokenRequest cliRequest);
//
//    @PostMapping(value = "${auth-server.users-uri}", consumes = MediaType.APPLICATION_JSON_VALUE)
//    ResponseEntity registerUser(KeycloakSignupRequest signupRequest, @RequestHeader("Authorization") String adminToken);

    private WebClient webClient;

    private final KeycloakConfig clientConfig;
    private final ObjectMapper objectMapper;

    @Autowired
    public KeycloakClient(KeycloakConfig clientConfig, ObjectMapper objectMapper) {
        this.clientConfig = clientConfig;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init(){
        webClient = WebClient.builder()
                .baseUrl(clientConfig.getUrl())
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL)
                ))
                .build();
    }

    public Mono<AuthResponse> getCliToken(KeycloakAdminTokenRequest adminTokenRequest) {
        return webClient
                .post()
                .uri(clientConfig.getCliTokenUri())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(BodyInserters.fromFormData(convertToForm(adminTokenRequest)))
                .retrieve()
                .bodyToMono(AuthResponse.class);
    }

    public Mono<URI> createAccount(KeycloakSignupRequest signupRequest, String adminToken) {
        return webClient
                .post()
                .uri(clientConfig.getUsersUri())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", adminToken)
                .body(BodyInserters.fromValue(signupRequest))
                .retrieve()
                .toBodilessEntity()
                .map(clientResponse -> clientResponse.getHeaders().getLocation());
    }

    MultiValueMap<String, String> convertToForm(Object obj) {
        MultiValueMap parameters = new LinkedMultiValueMap<String, String>();
        Map<String, String> maps = objectMapper.convertValue(obj, new TypeReference<>() {});
        parameters.setAll(maps);
        return parameters;
    }
}

