package com.agenatech.keycloakadminadapter.service;

import com.agenatech.keycloakadminadapter.model.payload.UserProfile;
import com.agenatech.keycloakadminadapter.model.payload.request.SignupRequest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface ProfileService {
    ResponseEntity signUp(UUID parentId, SignupRequest signupRequest);
    ResponseEntity<Object> createProfile(UUID parentId, UUID profileId, UserProfile userProfile);
}
