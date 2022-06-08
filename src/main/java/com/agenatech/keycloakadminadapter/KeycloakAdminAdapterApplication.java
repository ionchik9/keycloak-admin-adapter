package com.agenatech.keycloakadminadapter;

import com.agenatech.keycloakadminadapter.config.KeycloakConfig;
import com.agenatech.keycloakadminadapter.config.ProfilesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ProfilesConfig.class, KeycloakConfig.class})
public class KeycloakAdminAdapterApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeycloakAdminAdapterApplication.class, args);
	}

}
