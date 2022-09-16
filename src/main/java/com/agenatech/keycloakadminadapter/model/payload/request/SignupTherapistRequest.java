package com.agenatech.keycloakadminadapter.model.payload.request;

import lombok.Data;

import java.util.List;

@Data
public class SignupTherapistRequest extends SignupRequest{

    private List<String> states;


    private List<String> groups;

}
