package com.agenatech.keycloakadminadapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class KeycloakAdminAdapterApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeycloakAdminAdapterApplication.class, args);
	}

}
