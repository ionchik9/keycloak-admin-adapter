package com.agenatech.keycloakadminadapter.service.impl;

import com.agenatech.keycloakadminadapter.client.ProfilesClient;
import com.agenatech.keycloakadminadapter.model.payload.UserProfile;
import com.agenatech.keycloakadminadapter.model.payload.request.SignupRequest;
import com.agenatech.keycloakadminadapter.service.KeycloakService;
import com.agenatech.keycloakadminadapter.service.ProfileService;
import com.agenatech.keycloakadminadapter.utils.UriUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService {
    private final KeycloakService keycloakService;
    private final ProfilesClient profilesClient;


    @Autowired
    public ProfileServiceImpl(KeycloakService keycloakService, ProfilesClient profilesClient) {
        this.keycloakService = keycloakService;
        this.profilesClient = profilesClient;
    }

    @Override
    public  Mono<UserProfile> signUp(UUID parentId, SignupRequest signupRequest) {
        return registerUser(signupRequest)
                .flatMap(userId -> createProfile(parentId, userId, signupRequestToUserProfile(signupRequest)));
    }

    @Override
    public Mono<UserProfile> createProfile(UUID parentId, String profileId, UserProfile userProfile) {
        userProfile.setParentId(parentId);
        return profilesClient.createProfile(profileId, userProfile);
    }


    private Mono<String> registerUser(SignupRequest request) {
        return keycloakService.signup(request)
                .map(UriUtils::getLocationId);
    }


    private UserProfile signupRequestToUserProfile(SignupRequest signupRequest){
        return UserProfile.builder()
                .email(signupRequest.getEmail())
                .firstName(signupRequest.getFirstName())
                .lastName(signupRequest.getLastName())
                .additionalDetails(signupRequest.getAdditionalDetails())
                .avatarUrl(signupRequest.getAvatarUrl())
                .mobileNumber(signupRequest.getMobileNumber())
                .build();
    }

}
