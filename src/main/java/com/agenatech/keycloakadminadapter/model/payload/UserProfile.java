package com.agenatech.keycloakadminadapter.model.payload;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class UserProfile {
    private UUID id;

    private String email;

    private String firstName;

    private String lastName;

    private UUID parentId;

    private String mobileNumber;

    private String additionalDetails;

    private String avatarUrl;

}
