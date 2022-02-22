package com.agenatech.keycloakadminadapter.controller;


import com.agenatech.keycloakadminadapter.client.KeycloakClient;
import com.agenatech.keycloakadminadapter.client.ProfilesClient;
import com.agenatech.keycloakadminadapter.config.KeycloakConfig;
import com.agenatech.keycloakadminadapter.controller.data.TestDataManager;
import com.agenatech.keycloakadminadapter.exception.ProfilesException;
import com.agenatech.keycloakadminadapter.model.payload.KeycloakCredentials;
import com.agenatech.keycloakadminadapter.model.payload.UserProfile;
import com.agenatech.keycloakadminadapter.model.payload.request.SignupRequest;
import com.agenatech.keycloakadminadapter.service.impl.KeycloakServiceImpl;
import com.agenatech.keycloakadminadapter.service.impl.ProfileServiceImpl;
import com.agenatech.keycloakadminadapter.utils.UriUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;


@RunWith(SpringRunner.class)
@WebFluxTest(controllers = SignupController.class)
@Import({ProfileServiceImpl.class, KeycloakServiceImpl.class, KeycloakConfig.class})
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class SignupControllerReactiveTests {
	@MockBean
	private ProfilesClient profilesClient;
	@MockBean
	private KeycloakClient keycloakClient;

	@Autowired
	private WebTestClient webTestClient;

	protected static final String CONTROLLER_URL_ROOT_PREFIX = "/api/v1/sso/";


	@Test
	public void createAccount() throws Exception{
		Mockito.when(keycloakClient.createAccount(any(), any())).thenReturn(Mono.just(new URI("dr")));
		Mockito.when(keycloakClient.getCliToken(any())).thenReturn(Mono.just(TestDataManager.generateAuthResponse()));

		webTestClient.post()
				.uri(CONTROLLER_URL_ROOT_PREFIX + "create-account/")
				.body(BodyInserters.fromValue(TestDataManager.generateKeycloakSignupRequest()))
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isCreated();
	}

	@Test
	public void createProfile() {
		Mockito.when(profilesClient.createProfile(any(), any())).thenReturn(Mono.just(UserProfile.builder().build()));
		UUID profileId = UUID.randomUUID();

		webTestClient.put()
				.uri(CONTROLLER_URL_ROOT_PREFIX +  UUID.randomUUID() + "/create-profile/" + profileId)
				.body(BodyInserters.fromValue(TestDataManager.generateUserProfile(profileId)))
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isCreated();
	}


	@Test
	public void createAccountAndProfile() throws Exception{
		Mockito.when(keycloakClient.createAccount(any(), any())).thenReturn(Mono.just(new URI("dr")));
		Mockito.when(keycloakClient.getCliToken(any())).thenReturn(Mono.just(TestDataManager.generateAuthResponse()));
		Mockito.when(profilesClient.createProfile(any(), any())).thenReturn(Mono.just(UserProfile.builder().build()));

		try (MockedStatic<UriUtils> mockedLocation = Mockito.mockStatic(UriUtils.class)) {
			mockedLocation
					.when( ()-> UriUtils.getLocationId(any()))
					.thenReturn(UUID.randomUUID().toString());

			webTestClient.post()
					.uri(CONTROLLER_URL_ROOT_PREFIX +  UUID.randomUUID() + "/create-account-profile/")
					.body(BodyInserters.fromValue(SignupRequest.builder().email("ddd").credentials(List.of(KeycloakCredentials.builder().build())).build()))
					.accept(MediaType.APPLICATION_JSON)
					.exchange()
					.expectStatus().isCreated();
		}
	}


	@Test
	public void createAccountAndProfileWithExceptionProfile() throws Exception{
		String locationResponse = UUID.randomUUID().toString();

		Mockito.when(keycloakClient.createAccount(any(), any())).thenReturn(Mono.just(new URI("dr")));
		Mockito.when(keycloakClient.getCliToken(any())).thenReturn(Mono.just(TestDataManager.generateAuthResponse()));
		Mockito.when(profilesClient.createProfile(any(), any())).thenThrow(new ProfilesException("blabla", locationResponse, HttpStatus.CONFLICT));

		try (MockedStatic<UriUtils> mockedLocation = Mockito.mockStatic(UriUtils.class)) {
			mockedLocation
					.when( ()-> UriUtils.getLocationId(any()))
					.thenReturn(locationResponse);


			String response = webTestClient.post()
					.uri(CONTROLLER_URL_ROOT_PREFIX +  UUID.randomUUID() + "/create-account-profile/")
					.body(BodyInserters.fromValue(SignupRequest.builder().email("ddd").credentials(List.of(KeycloakCredentials.builder().build())).build()))
					.accept(MediaType.APPLICATION_JSON)
					.exchange()
					.expectStatus().is4xxClientError()
					.returnResult(String.class)
					.getResponseBody()
					.blockFirst();

			assertThat(response).contains(locationResponse);
		}
	}

}