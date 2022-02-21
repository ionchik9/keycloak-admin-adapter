package com.agenatech.keycloakadminadapter.model.payload;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeycloakCredentials {
    @NotBlank
    private String type;
    @NotBlank
    private String value;
    @NotBlank
    boolean temporary;
}
