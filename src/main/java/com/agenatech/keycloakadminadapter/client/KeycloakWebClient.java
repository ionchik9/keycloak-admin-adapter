package com.agenatech.keycloakadminadapter.client;


import com.agenatech.keycloakadminadapter.config.KeycloakConfig;
import com.agenatech.keycloakadminadapter.model.payload.request.keycloak.KeycloakSignupRequest;
import com.agenatech.keycloakadminadapter.model.payload.response.AuthResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
public class KeycloakWebClient {
    @Autowired
    private KeycloakConfig clientConfig;
    private WebClient webClient;
    @Autowired
    private ObjectMapper objectMapper;



    @PostConstruct
    public void init(){
        webClient = WebClient.builder()
                .baseUrl(clientConfig.getUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
    }


    public Mono<AuthResponse> registerUser(KeycloakSignupRequest signupRequest, String adminToken) {
        return webClient
                .post()
                .uri(clientConfig.getUsersUri())
                .header("Authorization", adminToken)
                .body(BodyInserters.fromValue(signupRequest))
                .retrieve()
                .bodyToMono(AuthResponse.class);
    }


    MultiValueMap<String, String> convertToForm(Object obj) {
        MultiValueMap parameters = new LinkedMultiValueMap<String, String>();
        Map<String, String> maps = objectMapper.convertValue(obj, new TypeReference<>() {});
        parameters.setAll(maps);
        return parameters;
    }
}
