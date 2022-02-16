package com.agenatech.keycloakadminadapter.model.payload.request.keycloak;

import com.agenatech.keycloakadminadapter.model.payload.KeycloakCredentials;
import lombok.*;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeycloakSignupRequest {
    private String email;
    private boolean enabled;
    private List<KeycloakCredentials> credentials;
}
