package com.agenatech.keycloakadminadapter.model.payload.request;

import com.agenatech.keycloakadminadapter.model.payload.KeycloakCredentials;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequest {
    @NotBlank
    private String email;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String additionalDetails;

    private Boolean enabled;
    private List<KeycloakCredentials> credentials;
}
