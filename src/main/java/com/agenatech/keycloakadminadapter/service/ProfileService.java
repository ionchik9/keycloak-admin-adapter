package com.agenatech.keycloakadminadapter.service;

import com.agenatech.keycloakadminadapter.model.UserProfile;
import com.agenatech.keycloakadminadapter.model.payload.request.SignupRequest;

public interface ProfileService {
    UserProfile signUp(SignupRequest signupRequest);
//    todo change response types below
}
