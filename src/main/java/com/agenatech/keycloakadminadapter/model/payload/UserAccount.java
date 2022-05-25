package com.agenatech.keycloakadminadapter.model.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {
    private UUID id;

    private String email;

    private boolean enabled;

    private boolean emailVerified;

    private long createdTimestamp;
}
