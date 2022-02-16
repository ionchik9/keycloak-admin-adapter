package com.agenatech.keycloakadminadapter.model;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor @Builder @ToString(onlyExplicitlyIncluded = true)
@Cacheable
@org.hibernate.annotations.Cache(usage =
        CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserProfile {
    @Id
    @Column(name = "id", updatable= false)
    @ToString.Include
    private UUID id;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    @ToString.Include
    private String email;


    @Column(name = "first_name")
    @ToString.Include
    private String firstName;

    @Column(name = "last_name")
    @ToString.Include
    private String lastName;

}
