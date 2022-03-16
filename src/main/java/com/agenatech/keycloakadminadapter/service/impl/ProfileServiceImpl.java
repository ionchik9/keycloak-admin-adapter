package com.agenatech.keycloakadminadapter.service.impl;

import com.agenatech.keycloakadminadapter.client.AchievementsClient;
import com.agenatech.keycloakadminadapter.client.ProfilesClient;
import com.agenatech.keycloakadminadapter.model.payload.UserProfile;
import com.agenatech.keycloakadminadapter.model.payload.request.SignupRequest;
import com.agenatech.keycloakadminadapter.service.KeycloakService;
import com.agenatech.keycloakadminadapter.service.ProfileService;
import com.agenatech.keycloakadminadapter.utils.UriUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService {
    private final KeycloakService keycloakService;
    private final ProfilesClient profilesClient;
    private final AchievementsClient achievementsClient;


    @Autowired
    public ProfileServiceImpl(KeycloakService keycloakService, ProfilesClient profilesClient, AchievementsClient achievementsClient) {
        this.keycloakService = keycloakService;
        this.profilesClient = profilesClient;
        this.achievementsClient = achievementsClient;
    }


    @Override
    public  Mono<UserProfile> signUp(UUID parentId, SignupRequest signupRequest) {
        return registerUser(signupRequest)
                .flatMap(userId -> createProfile(parentId, userId, signupRequestToUserProfile(signupRequest)));
    }

    @Override
//    todo remove achievement after kafka is implemented
    public Mono<UserProfile> createProfile(UUID parentId, String profileId, UserProfile userProfile) {
        userProfile.setParentId(parentId);
        return achievementsClient.scheduleUserAchievements(profileId)
                .then(profilesClient.createProfile(profileId, userProfile));
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
