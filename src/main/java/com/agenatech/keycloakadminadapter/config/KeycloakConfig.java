package com.agenatech.keycloakadminadapter.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "auth-server")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakConfig {
        private String url;
        private String adminSecret;
        private String cliTokenUri;
        private String usersUri;
}


