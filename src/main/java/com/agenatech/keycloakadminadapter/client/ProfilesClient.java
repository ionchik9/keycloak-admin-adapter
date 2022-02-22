package com.agenatech.keycloakadminadapter.client;


import com.agenatech.keycloakadminadapter.config.ProfilesConfig;
import com.agenatech.keycloakadminadapter.model.payload.UserProfile;
import io.netty.handler.logging.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import javax.annotation.PostConstruct;


@Service
public class ProfilesClient {

    private WebClient webClient;

    private final ProfilesConfig profilesConfig;

    public ProfilesClient(ProfilesConfig profilesConfig) {
        this.profilesConfig = profilesConfig;
    }

    @Autowired


    @PostConstruct
    public void init(){
        webClient = WebClient.builder()
                .baseUrl(profilesConfig.getUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL)
                ))
                .build();
    }

    public Mono<UserProfile> createProfile(String id, UserProfile userProfile) {
        return webClient
                .put()
                .uri(profilesConfig.getPath()+"/{id}", id)
                .body(BodyInserters.fromValue(userProfile))
                .retrieve()
                .bodyToMono(UserProfile.class);
//                .onErrorReturn()
    }
}

