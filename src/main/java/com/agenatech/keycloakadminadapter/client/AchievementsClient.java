package com.agenatech.keycloakadminadapter.client;


import com.agenatech.keycloakadminadapter.config.AchievementsConfig;
import com.agenatech.keycloakadminadapter.exception.AchievementsException;
import com.agenatech.keycloakadminadapter.exception.ErrorDto;
import io.netty.handler.logging.LogLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import javax.annotation.PostConstruct;


@Service
public class AchievementsClient {

    private WebClient webClient;

    private final AchievementsConfig achievementsConfig;

    @Autowired
    public AchievementsClient(AchievementsConfig achievementsConfig) {
        this.achievementsConfig = achievementsConfig;
    }


    @PostConstruct
    public void init(){
        webClient = WebClient.builder()
                .baseUrl(achievementsConfig.getUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().wiretap("reactor.netty.http.client.HttpClient", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL)
                ))
                .build();
    }

    public Mono<Void> scheduleUserAchievements(String userId) {
        return webClient
                .post()
                .uri(achievementsConfig.getSchedulePath()+"/{userId}/schedule-achievements", userId)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> response.bodyToMono(ErrorDto.class)
                        .flatMap(error -> Mono.error(new AchievementsException(error.message(), userId, response.statusCode()))))
                .bodyToMono(Void.class);
    }
}

