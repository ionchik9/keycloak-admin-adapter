package com.agenatech.keycloakadminadapter.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

@ConfigurationProperties(prefix = "auth-server")
@Profile("!test")
public record KeycloakConfig(String url, String adminSecret, String cliTokenUri, String usersUri, String therapistGroupName) {
}


