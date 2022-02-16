package com.agenatech.keycloakadminadapter.model.payload.request.keycloak;

import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeycloakAdminTokenRequest {
    private String client_id;
    private String grant_type;
    private String client_secret;
}
