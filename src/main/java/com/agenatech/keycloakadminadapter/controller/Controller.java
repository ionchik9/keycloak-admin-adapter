package com.agenatech.keycloakadminadapter.controller;

import com.agenatech.keycloakadminadapter.model.payload.TherapistProfile;
import com.agenatech.keycloakadminadapter.model.payload.UserAccount;
import com.agenatech.keycloakadminadapter.model.payload.UserProfile;
import com.agenatech.keycloakadminadapter.model.payload.request.SignupRequest;
import com.agenatech.keycloakadminadapter.model.payload.request.SignupTherapistRequest;
import com.agenatech.keycloakadminadapter.model.payload.request.keycloak.KeycloakSignupRequest;
import com.agenatech.keycloakadminadapter.service.KeycloakService;
import com.agenatech.keycloakadminadapter.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/sso")
public class Controller {
    private final ProfileService profileService;
    private final KeycloakService keycloakService;

    @Autowired
    public Controller(ProfileService profileService, KeycloakService keycloakService) {
        this.profileService = profileService;
        this.keycloakService = keycloakService;
    }


    @PostMapping("/accounts")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<URI> createAccount(@Valid @RequestBody KeycloakSignupRequest keycloakSignupRequest) {
        return  keycloakService.signup(keycloakSignupRequest);
    }

    @PutMapping("/create-profile/{profileId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserProfile> registerUser(@PathVariable UUID profileId, @Valid @RequestBody UserProfile userProfile) {
        return profileService.createProfile(profileId.toString(), userProfile);
    }

    @PostMapping("/create-account-profile")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserProfile>createAccountAndProfile(@Valid @RequestBody SignupRequest signupRequest)  {
        return  profileService.signUp(signupRequest);
    }

    @PostMapping("/admin/therapists")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TherapistProfile>createTherapist(@Valid @RequestBody SignupTherapistRequest signupRequest)  {
        return profileService.createTherapist(signupRequest);
    }

    @GetMapping("/admin/accounts/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserAccount> getAccount(@PathVariable UUID accountId) {
        return keycloakService.getAccount(accountId);
    }

    @DeleteMapping("/admin/clients/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity> deleteUserAccount(@PathVariable UUID accountId){
        return profileService.deleteUser(accountId);
    }

    @DeleteMapping("/accounts/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMyAccount(@PathVariable UUID accountId){
         profileService.deleteUser(accountId);
    }

    @DeleteMapping("/admin/therapists/{accountId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTherapist(@PathVariable UUID accountId){
        profileService.deleteTherapist(accountId);
    }
}
