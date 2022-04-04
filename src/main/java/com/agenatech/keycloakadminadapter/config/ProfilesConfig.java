package com.agenatech.keycloakadminadapter.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "profiles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfilesConfig {
    private String path;
    private String url;
}