package com.agenatech.keycloakadminadapter.service.impl;

import com.agenatech.keycloakadminadapter.client.ProfilesClient;
import com.agenatech.keycloakadminadapter.config.KeycloakConfig;
import com.agenatech.keycloakadminadapter.exception.ProfilesException;
import com.agenatech.keycloakadminadapter.model.payload.TherapistProfile;
import com.agenatech.keycloakadminadapter.model.payload.UserProfile;
import com.agenatech.keycloakadminadapter.model.payload.request.SignupRequest;
import com.agenatech.keycloakadminadapter.model.payload.request.SignupTherapistRequest;
import com.agenatech.keycloakadminadapter.service.KeycloakService;
import com.agenatech.keycloakadminadapter.service.ProfileService;
import com.agenatech.keycloakadminadapter.utils.UriUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final KeycloakService keycloakService;
    private final ProfilesClient profilesClient;
    private final KeycloakConfig keycloakConfig;



    @Override
    public Mono<UserProfile> signUp(SignupRequest signupRequest) {
        return registerUser(signupRequest)
                .flatMap(userId -> createProfile(userId, signupRequestToProfile(signupRequest, UserProfile.class)));
    }

    @Override
    public Mono<TherapistProfile> createTherapist(SignupTherapistRequest signupRequest) {
        signupRequest.setGroups(List.of(keycloakConfig.therapistGroupName()));
        return registerUser(signupRequest)
                .flatMap(userId -> createTherapistProfile(userId, signupRequestToProfile(signupRequest, TherapistProfile.class)));
    }

    @Override
    public Mono<UserProfile> createProfile(String profileId, UserProfile userProfile) {
        log.debug(" to create profile {}", profileId);
//        userProfile.setParentId(parentId);
        return profilesClient.createProfile(profileId, userProfile);
    }

    @Override
    public Mono<TherapistProfile> createTherapistProfile(String profileId, TherapistProfile profile) {
        return profilesClient.createTherapistProfile(profileId, profile);
    }

    @Override
    public Mono<ResponseEntity> deleteUser(UUID userId) {
        log.debug(" to delete user {}", userId);
         return profilesClient.deleteProfile(userId)
                .onErrorMap(error -> new ProfilesException(error.getMessage(), userId.toString(), HttpStatus.BAD_REQUEST))
                .doOnSuccess(x ->keycloakService.deleteAccount(userId));
    }

    @Override
    public void deleteTherapist(UUID userId) {
         profilesClient.deleteTherapistProfile(userId)
                .onErrorMap(error -> new ProfilesException(error.getMessage(), userId.toString(), HttpStatus.BAD_REQUEST))
                .doOnSuccess(x ->keycloakService.deleteAccount(userId))
                .subscribe();
    }


    private <T extends SignupRequest> Mono<String> registerUser(T request) {
        return keycloakService.signup(request)
                .map(UriUtils::getLocationId);
    }


    @SneakyThrows
    private  <T extends SignupRequest, C> C signupRequestToProfile(T signupRequest, Class<C> clazz) {
        var profile = clazz.getDeclaredConstructor().newInstance();
        BeanUtils.copyProperties(profile, signupRequest);
        return profile;
    }

}
