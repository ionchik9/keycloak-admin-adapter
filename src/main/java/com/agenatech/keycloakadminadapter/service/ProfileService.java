package com.agenatech.keycloakadminadapter.service;

import com.agenatech.keycloakadminadapter.model.payload.TherapistProfile;
import com.agenatech.keycloakadminadapter.model.payload.UserProfile;
import com.agenatech.keycloakadminadapter.model.payload.request.SignupRequest;
import com.agenatech.keycloakadminadapter.model.payload.request.SignupTherapistRequest;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProfileService {
    Mono<UserProfile>signUp(UUID parentId, SignupRequest signupRequest);
    Mono<TherapistProfile> createTherapist(SignupTherapistRequest signupRequest);
    Mono<UserProfile> createProfile(UUID parentId, String profileId, UserProfile userProfile);
    Mono<TherapistProfile> createTherapistProfile(String profileId, TherapistProfile profile);
    Mono<Void> deleteUser(UUID userId);
}
