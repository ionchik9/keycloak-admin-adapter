package com.agenatech.keycloakadminadapter.controller;


import com.agenatech.keycloakadminadapter.client.AchievementsClient;
import com.agenatech.keycloakadminadapter.client.KeycloakClient;
import com.agenatech.keycloakadminadapter.client.ProfilesClient;
import com.agenatech.keycloakadminadapter.controller.data.TestDataManager;
import com.agenatech.keycloakadminadapter.model.payload.KeycloakCredentials;
import com.agenatech.keycloakadminadapter.model.payload.UserProfile;
import com.agenatech.keycloakadminadapter.model.payload.request.SignupRequest;
import com.agenatech.keycloakadminadapter.model.payload.response.AuthResponse;
import com.agenatech.keycloakadminadapter.utils.UriUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
@ActiveProfiles("test")
class SignupControllerTests {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private TestDataManager testDataManager;
	@MockBean
	private ProfilesClient profilesClient;
	@MockBean
	private KeycloakClient keycloakClient;
	@MockBean
	private AchievementsClient achievementsClient;

	protected static final String CONTROLLER_URL_ROOT_PREFIX = "/api/v1/sso/";



	@Test
	public void createAccount() throws Exception{
		Mockito.when(keycloakClient.registerUser(any(), any())).thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());
		Mockito.when(keycloakClient.getCliToken(any())).thenReturn(new AuthResponse());


		this.mockMvc.perform(post(CONTROLLER_URL_ROOT_PREFIX + "create-account/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(serialize(testDataManager.generateKeycloakSignupRequest())))
				.andExpect(status().isCreated());
	}

	@Test
	public void createProfile() throws Exception{
		Mockito.when(profilesClient.createProfile(any(), any())).thenReturn(UserProfile.builder().build());
		Mockito.when(keycloakClient.getCliToken(any())).thenReturn(new AuthResponse());
		Mockito.when(achievementsClient.scheduleAchievements(any())).thenReturn(ResponseEntity.status(HttpStatus.OK).build());

		this.mockMvc.perform(put(CONTROLLER_URL_ROOT_PREFIX + UUID.randomUUID() + "/create-profile/" + UUID.randomUUID())
						.contentType(MediaType.APPLICATION_JSON)
						.content(serialize(testDataManager.generateUserProfile(UUID.randomUUID()))))
				.andExpect(status().isCreated());
	}


	@Test
	public void createAccountAndProfile() throws Exception{
		Mockito.when(keycloakClient.registerUser(any(), any())).thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());
		Mockito.when(keycloakClient.getCliToken(any())).thenReturn(new AuthResponse());
		Mockito.when(profilesClient.createProfile(any(), any())).thenReturn(UserProfile.builder().build());

		try (MockedStatic<UriUtils> mockedLocation = Mockito.mockStatic(UriUtils.class)) {
			mockedLocation
					.when( ()-> UriUtils.getLocationId(any()))
					.thenReturn(UUID.randomUUID().toString());

			this.mockMvc.perform(post(CONTROLLER_URL_ROOT_PREFIX + UUID.randomUUID() + "/create-account-profile")
							.contentType(MediaType.APPLICATION_JSON)
							.content(serialize(SignupRequest.builder().email("ddd").credentials(List.of(KeycloakCredentials.builder().build())).build())))
					.andExpect(status().isCreated());
		}
	}


	@Test
	public void createAccountAndProfileWithExceptionProfile() throws Exception{
		Mockito.when(keycloakClient.registerUser(any(), any())).thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());
		Mockito.when(keycloakClient.getCliToken(any())).thenReturn(new AuthResponse());
		Mockito.when(profilesClient.createProfile(any(), any())).thenThrow(new RuntimeException("blabla"));

		String locationResponse = UUID.randomUUID().toString();

		try (MockedStatic<UriUtils> mockedLocation = Mockito.mockStatic(UriUtils.class)) {
			mockedLocation
					.when( ()-> UriUtils.getLocationId(any()))
					.thenReturn(locationResponse);

			this.mockMvc.perform(post(CONTROLLER_URL_ROOT_PREFIX + UUID.randomUUID() + "/create-account-profile")
							.contentType(MediaType.APPLICATION_JSON)
							.content(serialize(SignupRequest.builder().email("ddd").credentials(List.of(KeycloakCredentials.builder().build())).build())))
					.andExpect(status().isInternalServerError())
					.andExpect(content().string(containsString(locationResponse)));
		}
	}



	private String serialize(Object entity) throws JsonProcessingException {
		ObjectMapper o = new ObjectMapper();
		return o.writeValueAsString(entity);
	}
}
