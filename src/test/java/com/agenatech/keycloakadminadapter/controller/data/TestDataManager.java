package com.agenatech.keycloakadminadapter.controller.data;

import com.agenatech.keycloakadminadapter.model.payload.KeycloakCredentials;
import com.agenatech.keycloakadminadapter.model.payload.UserProfile;
import com.agenatech.keycloakadminadapter.model.payload.request.keycloak.KeycloakSignupRequest;
import com.agenatech.keycloakadminadapter.model.payload.response.AuthResponse;

import java.util.List;
import java.util.UUID;


public class TestDataManager {

    public static UserProfile generateUserProfile(UUID id){
        return UserProfile.builder()
                .firstName("testttt" + id)
                .email(id +"@email.com")
                .build();
    }


    public static KeycloakSignupRequest generateKeycloakSignupRequest(){
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

    public static AuthResponse generateAuthResponse(){
        return new AuthResponse("","","",300, 1800);
    }
}
