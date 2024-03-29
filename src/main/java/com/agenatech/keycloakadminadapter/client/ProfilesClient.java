package com.agenatech.keycloakadminadapter.client;


import com.agenatech.keycloakadminadapter.model.KeycloakRequiredAction;
import com.agenatech.keycloakadminadapter.model.payload.UserProfile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "profiles", url = "${profiles.url}")
@Service
public interface ProfilesClient {

    @PutMapping(value = "${profiles.path}/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Object> createProfile(@PathVariable("id") UUID id, UserProfile userProfile);

    @PutMapping(value = "${auth-server.email-actions-uri}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity emailAction(@PathVariable("id") String id, List<KeycloakRequiredAction> actions, @RequestHeader("Authorization") String adminToken);


}

