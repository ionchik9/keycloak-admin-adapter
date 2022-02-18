package com.agenatech.keycloakadminadapter.controller;

import com.agenatech.keycloakadminadapter.model.payload.UserProfile;
import com.agenatech.keycloakadminadapter.model.payload.request.SignupRequest;
import com.agenatech.keycloakadminadapter.model.payload.request.keycloak.KeycloakSignupRequest;
import com.agenatech.keycloakadminadapter.service.KeycloakService;
import com.agenatech.keycloakadminadapter.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/sso")
public class SignupController {
    private final ProfileService profileService;
    private final KeycloakService keycloakService;

    @Autowired
    public SignupController(ProfileService profileService, KeycloakService keycloakService) {
        this.profileService = profileService;
        this.keycloakService = keycloakService;
    }


    @PostMapping("/create-account")
    public ResponseEntity createAccount(@Valid @RequestBody KeycloakSignupRequest keycloakSignupRequest) {
        return  keycloakService.signup(keycloakSignupRequest);
    }

    @PutMapping("{parentId}/create-profile/{profileId}")
    public ResponseEntity<UserProfile> registerUser(@PathVariable UUID parentId, @PathVariable UUID profileId, @Valid @RequestBody UserProfile userProfile) {
        return ResponseEntity.status(HttpStatus.CREATED).body(profileService.createProfile(parentId, profileId, userProfile));
    }

    @PostMapping("{parentId}/create-account-profile")
    public ResponseEntity<UserProfile>createAccountAndProfile(@PathVariable UUID parentId, @Valid @RequestBody SignupRequest signupRequest) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(profileService.signUp(parentId, signupRequest));
    }
}
