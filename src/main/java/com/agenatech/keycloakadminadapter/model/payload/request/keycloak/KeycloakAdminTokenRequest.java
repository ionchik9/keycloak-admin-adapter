package com.agenatech.keycloakadminadapter.model.payload.request.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeycloakAdminTokenRequest {
    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("grant_type")
    private String grantType;

    @JsonProperty("client_secret")
    private String clientSecret;
}
