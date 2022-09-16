package com.agenatech.keycloakadminadapter.model.payload.request.keycloak;

import com.agenatech.keycloakadminadapter.model.payload.KeycloakCredentials;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeycloakSignupRequest {
    @NotBlank
    private String email;
    private boolean enabled;
    @NotNull
    private List<KeycloakCredentials> credentials;

    private List<String> groups;
}
