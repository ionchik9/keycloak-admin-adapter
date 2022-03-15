package com.agenatech.keycloakadminadapter.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(name = "achievements", url = "${achievements.url}")
@Service
public interface AchievementsClient {

    @PostMapping(value = "${achievements.schedule-path}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity scheduleAchievements(@PathVariable("userId") UUID userId);
}
