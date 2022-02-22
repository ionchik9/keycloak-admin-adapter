package com.agenatech.keycloakadminadapter.service;

import com.agenatech.keycloakadminadapter.model.payload.UserProfile;
import com.agenatech.keycloakadminadapter.model.payload.request.SignupRequest;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProfileService {
    Mono<UserProfile>signUp(UUID parentId, SignupRequest signupRequest);
    Mono<UserProfile> createProfile(UUID parentId, Mono<String> profileId, UserProfile userProfile);
}
