package com.agenatech.keycloakadminadapter.client;


import com.agenatech.keycloakadminadapter.config.ProfilesConfig;
import com.agenatech.keycloakadminadapter.exception.ErrorDto;
import com.agenatech.keycloakadminadapter.exception.ProfilesException;
import com.agenatech.keycloakadminadapter.model.payload.TherapistProfile;
import com.agenatech.keycloakadminadapter.model.payload.UserProfile;
import io.netty.handler.logging.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import javax.annotation.PostConstruct;
import java.util.UUID;


@Service
public class ProfilesClient {

    private WebClient webClient;

    private final ProfilesConfig profilesConfig;

    @Autowired
    public ProfilesClient(ProfilesConfig profilesConfig) {
        this.profilesConfig = profilesConfig;
    }




    @PostConstruct
    public void init(){
        webClient = WebClient.builder()
                .baseUrl(profilesConfig.url())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL)
                ))
                .build();
    }

    public Mono<UserProfile> createProfile(String id, UserProfile userProfile) {
        return webClient
                .put()
                .uri(profilesConfig.path()+"/{id}", id)
                .body(BodyInserters.fromValue(userProfile))
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(ErrorDto.class)
                        .flatMap(error -> Mono.error(new ProfilesException(error.message(), id, response.statusCode()))))
                .bodyToMono(UserProfile.class);
    }

    public Mono<TherapistProfile> createTherapistProfile(String id, TherapistProfile profile) {
        return webClient
                .put()
                .uri(profilesConfig.therapistPath()+"/{id}", id)
                .body(BodyInserters.fromValue(profile))
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(ErrorDto.class)
                        .flatMap(error -> Mono.error(new ProfilesException(error.message(), id, response.statusCode()))))
                .bodyToMono(TherapistProfile.class);
    }

    public Mono<Void> deleteProfile(UUID profileId){
        return webClient
                .delete()
                .uri(profilesConfig.path()+"/{profileId}", profileId)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(ErrorDto.class)
                        .flatMap(error -> Mono.error(new ProfilesException(error.message(), profileId.toString(), response.statusCode()))))
                .bodyToMono(Void.class);
    }

    public Mono<Void> deleteTherapistProfile(UUID profileId){
        return webClient
                .delete()
                .uri(profilesConfig.therapistPath()+"/{profileId}", profileId)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(ErrorDto.class)
                        .flatMap(error -> Mono.error(new ProfilesException(error.message(), profileId.toString(), response.statusCode()))))
                .bodyToMono(Void.class);
    }
}

