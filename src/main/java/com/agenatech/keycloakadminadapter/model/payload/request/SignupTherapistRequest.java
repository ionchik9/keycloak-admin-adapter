package com.agenatech.keycloakadminadapter.model.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class SignupTherapistRequest extends SignupRequest{

    private List<String> states;

    @Schema(hidden = true)
    private List<String> groups;

}
