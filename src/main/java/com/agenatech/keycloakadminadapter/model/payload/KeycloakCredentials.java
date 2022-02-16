package com.agenatech.keycloakadminadapter.model.payload;

import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeycloakCredentials {
    private String type;
    private String value;
    boolean temporary;
}
