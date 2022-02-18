package com.agenatech.keycloakadminadapter.model.payload;


import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserProfile {

    private String email;

    private String firstName;

    private String lastName;

    private UUID parentId;

    private String mobileNumber;

    private String additionalDetails;

    private String avatarUrl;

}
