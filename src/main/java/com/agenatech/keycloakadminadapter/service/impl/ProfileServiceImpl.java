package com.agenatech.keycloakadminadapter.service.impl;

import com.agenatech.keycloakadminadapter.client.ProfilesClient;
import com.agenatech.keycloakadminadapter.exception.ProfilesException;
import com.agenatech.keycloakadminadapter.model.payload.UserProfile;
import com.agenatech.keycloakadminadapter.model.payload.request.SignupRequest;
import com.agenatech.keycloakadminadapter.service.KeycloakService;
import com.agenatech.keycloakadminadapter.service.ProfileService;
import com.agenatech.keycloakadminadapter.utils.UriUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public UserProfile signUp(UUID parentId, SignupRequest signupRequest) {
        UUID keycloackUserId = UUID.fromString(registerUser(signupRequest));
        try {
            return createProfile(parentId, keycloackUserId, signupRequestToUserProfile(signupRequest));
        } catch (Exception e){
            log.error("isssssuuuu");
            throw new ProfilesException("Error during profile creation, try the create profile operation, profileId = " + keycloackUserId, e.getCause());
        }

    }

    @Override
    public UserProfile createProfile(UUID parentId, UUID profileId, UserProfile userProfile) {
        userProfile.setParentId(parentId);
        return profilesClient.createProfile(profileId, userProfile);
    }


    private String registerUser(SignupRequest request) {
        ResponseEntity response = keycloakService.signup(request);
        return  UriUtils.getLocationId(response);
    }


    private UserProfile signupRequestToUserProfile(SignupRequest signupRequest){
        return UserProfile.builder()
                .email(signupRequest.getEmail())
                .firstName(signupRequest.getFirstName())
                .lastName(signupRequest.getLastName())
                .build();
    }

}
