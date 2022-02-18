package com.agenatech.keycloakadminadapter.service;

import com.agenatech.keycloakadminadapter.model.payload.UserProfile;
import com.agenatech.keycloakadminadapter.model.payload.request.SignupRequest;

import java.util.UUID;

public interface ProfileService {
    UserProfile signUp(UUID parentId, SignupRequest signupRequest);
    UserProfile createProfile(UUID parentId, UUID profileId, UserProfile userProfile);
}
