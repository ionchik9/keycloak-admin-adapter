package com.agenatech.keycloakadminadapter.model.payload;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class UserProfile {
    @NotBlank
    private String email;

    private String firstName;

    private String lastName;

    private UUID parentId;

    private String mobileNumber;

    private String additionalDetails;

    private String avatarUrl;

}
