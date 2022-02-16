package com.agenatech.keycloakadminadapter.service.impl;

import com.agenatech.keycloakadminadapter.model.UserProfile;
import com.agenatech.keycloakadminadapter.model.payload.request.SignupRequest;
import com.agenatech.keycloakadminadapter.service.KeycloakService;
import com.agenatech.keycloakadminadapter.service.ProfileService;
import com.agenatech.keycloakadminadapter.service.UriUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private KeycloakService keycloakService;
    @Autowired
    private UriUtils uriUtils;



    public UserProfile signUp(SignupRequest signupRequest) {
        UUID keycloackUserId = UUID.fromString(registerUser(signupRequest));
        return persistProfile(signupRequest, keycloackUserId);
    }

    public UserProfile persistProfile(SignupRequest signupRequest, UUID userId){
//        return userRepository.save(
//                UserProfile.builder()
//                        .id(userId)
//                        .email(signupRequest.getEmail())
//                        .firstName(signupRequest.getFirstName())
//                        .lastName(signupRequest.getLastName())
//                        .build());
        return null;
    }

    private String registerUser(SignupRequest request) {
        ResponseEntity response = keycloakService.signup(request);
        return  uriUtils.getLocationId(response);
    }




}
