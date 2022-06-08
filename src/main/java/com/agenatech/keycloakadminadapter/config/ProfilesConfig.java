package com.agenatech.keycloakadminadapter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

@ConfigurationProperties(prefix = "profiles")
@Profile("!test")
public record ProfilesConfig(String path, String url){
}
