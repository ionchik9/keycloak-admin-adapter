package com.agenatech.keycloakadminadapter.controller.data;

import com.agenatech.keycloakadminadapter.model.payload.KeycloakCredentials;
import com.agenatech.keycloakadminadapter.model.payload.UserProfile;
import com.agenatech.keycloakadminadapter.model.payload.request.keycloak.KeycloakSignupRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TestDataManager {

    public UserProfile generateUserProfile(UUID id){
        return UserProfile.builder()
                .firstName("testttt" + id)
                .email(id +"@email.com")
                .build();
    }


    public KeycloakSignupRequest generateKeycloakSignupRequest(){
        return KeycloakSignupRequest.builder()
                .username("test")
                .enabled(true)
                .credentials(List.of(KeycloakCredentials.builder()
                        .temporary(false)
                        .value("pass")
                        .type("password")
                        .build()))
                .build();
    }
}
