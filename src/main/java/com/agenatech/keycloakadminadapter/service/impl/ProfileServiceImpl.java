package com.agenatech.keycloakadminadapter.service.impl;

import com.agenatech.keycloakadminadapter.client.ProfilesClient;
import com.agenatech.keycloakadminadapter.model.payload.UserProfile;
import com.agenatech.keycloakadminadapter.model.payload.request.SignupRequest;
import com.agenatech.keycloakadminadapter.service.KeycloakService;
import com.agenatech.keycloakadminadapter.service.ProfileService;
import com.agenatech.keycloakadminadapter.utils.UriUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final KeycloakService keycloakService;
    private final ProfilesClient profilesClient;



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

//    todo error handling
    @Override
    public Mono<Void> deleteUser(UUID userId) {
        return keycloakService.deleteAccount(userId)
                .then(profilesClient.deleteProfile(userId));
    }


    private Mono<String> registerUser(SignupRequest request) {
        return keycloakService.signup(request)
                .map(UriUtils::getLocationId);
    }


    @SneakyThrows
    private UserProfile signupRequestToUserProfile(SignupRequest signupRequest) {
        var profile = new UserProfile();
        BeanUtils.copyProperties(profile, signupRequest);
        return profile;
    }

}
