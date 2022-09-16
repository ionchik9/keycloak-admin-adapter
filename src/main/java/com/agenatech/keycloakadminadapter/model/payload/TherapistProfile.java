package com.agenatech.keycloakadminadapter.model.payload;


import com.agenatech.keycloakadminadapter.model.payload.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class TherapistProfile {
    private UUID id;

    private String email;

    private String firstName;

    private String lastName;

    private Language language;


    private String mobileNumber;

    private String additionalDetails;

    private String avatarUrl;

    private List<String> states;

}