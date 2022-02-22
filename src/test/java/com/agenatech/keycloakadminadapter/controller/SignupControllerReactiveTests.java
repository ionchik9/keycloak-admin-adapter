//package com.agenatech.keycloakadminadapter.controller;//package com.agenatech.keycloakadminadapter.controller;
//
//
//import com.agenatech.keycloakadminadapter.client.KeycloakClient;
//import com.agenatech.keycloakadminadapter.client.ProfilesClient;
//import com.agenatech.keycloakadminadapter.controller.data.TestDataManager;
//import com.agenatech.keycloakadminadapter.model.payload.KeycloakCredentials;
//import com.agenatech.keycloakadminadapter.model.payload.UserProfile;
//import com.agenatech.keycloakadminadapter.model.payload.request.SignupRequest;
//import com.agenatech.keycloakadminadapter.model.payload.response.AuthResponse;
//import com.agenatech.keycloakadminadapter.utils.UriUtils;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.jupiter.api.Test;
//import org.mockito.MockedStatic;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.reactive.server.WebTestClient;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.util.UriBuilder;
//import reactor.core.publisher.Mono;
//
//import java.net.URI;
//import java.util.List;
//import java.util.UUID;
//
//import static org.mockito.ArgumentMatchers.any;
//
//
//@ExtendWith(SpringExtension.class)
//@WebFluxTest(controllers = SignupController.class)
//class SignupControllerReactiveTests {
////	@Autowired
////	private MockMvc mockMvc;
//	@Autowired
//	private TestDataManager testDataManager;
//	@MockBean
//	private ProfilesClient profilesClient;
//	@MockBean
//	private KeycloakClient keycloakClient;
//
//	@Autowired
//	private WebTestClient webTestClient;
//
//	protected static final String CONTROLLER_URL_ROOT_PREFIX = "/api/v1/sso/";
//
////    WebTestClient testClient = WebTestClient.bindToController(controller).build();
//
//
//
//	@Test
//	public void createAccount() throws Exception{
//		Mockito.when(keycloakClient.createAccount(any(), any())).thenReturn(Mono.just(new URI("dr")));
//		Mockito.when(keycloakClient.getCliToken(any())).thenReturn(Mono.just(new AuthResponse()));
//
//		String response = webTestClient.post()
//				.uri("/api/v1/my")
//				.body(BodyInserters.fromObject("is"))
//				.accept(MediaType.APPLICATION_JSON)
//				.exchange()
//				.expectStatus().isOk()
//				.returnResult(String.class)
//				.getResponseBody()
//				.blockFirst();
//
//		this.mockMvc.perform(post(CONTROLLER_URL_ROOT_PREFIX + "create-account/")
//						.contentType(MediaType.APPLICATION_JSON)
//						.content(serialize(testDataManager.generateKeycloakSignupRequest())))
//				.andExpect(status().isCreated());
//	}
//
//	@Test
//	public void createProfile() throws Exception{
//		Mockito.when(profilesClient.createProfile(any(), any())).thenReturn(UserProfile.builder().build());
//
//		this.mockMvc.perform(put(CONTROLLER_URL_ROOT_PREFIX + UUID.randomUUID() + "/create-profile/" + UUID.randomUUID())
//						.contentType(MediaType.APPLICATION_JSON)
//						.content(serialize(testDataManager.generateUserProfile(UUID.randomUUID()))))
//				.andExpect(status().isCreated());
//	}
//
//
//	@Test
//	public void createAccountAndProfile() throws Exception{
//		Mockito.when(keycloakClient.registerUser(any(), any())).thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());
//		Mockito.when(keycloakClient.getCliToken(any())).thenReturn(new AuthResponse());
//		Mockito.when(profilesClient.createProfile(any(), any())).thenReturn(UserProfile.builder().build());
//
//		try (MockedStatic<UriUtils> mockedLocation = Mockito.mockStatic(UriUtils.class)) {
//			mockedLocation
//					.when( ()-> UriUtils.getLocationId(any()))
//					.thenReturn(UUID.randomUUID().toString());
//
//			this.mockMvc.perform(post(CONTROLLER_URL_ROOT_PREFIX + UUID.randomUUID() + "/create-account-profile")
//							.contentType(MediaType.APPLICATION_JSON)
//							.content(serialize(SignupRequest.builder().email("ddd").credentials(List.of(KeycloakCredentials.builder().build())).build())))
//					.andExpect(status().isCreated());
//		}
//	}
//
//
//	@Test
//	public void createAccountAndProfileWithExceptionProfile() throws Exception{
//		Mockito.when(keycloakClient.registerUser(any(), any())).thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());
//		Mockito.when(keycloakClient.getCliToken(any())).thenReturn(new AuthResponse());
//		Mockito.when(profilesClient.createProfile(any(), any())).thenThrow(new RuntimeException("blabla"));
//
//		String locationResponse = UUID.randomUUID().toString();
//
//		try (MockedStatic<UriUtils> mockedLocation = Mockito.mockStatic(UriUtils.class)) {
//			mockedLocation
//					.when( ()-> UriUtils.getLocationId(any()))
//					.thenReturn(locationResponse);
//
//			this.mockMvc.perform(post(CONTROLLER_URL_ROOT_PREFIX + UUID.randomUUID() + "/create-account-profile")
//							.contentType(MediaType.APPLICATION_JSON)
//							.content(serialize(SignupRequest.builder().email("ddd").credentials(List.of(KeycloakCredentials.builder().build())).build())))
//					.andExpect(status().isInternalServerError())
//					.andExpect(content().string(containsString(locationResponse)));
//		}
//	}
//
//
//
//	private String serialize(Object entity) throws JsonProcessingException {
//		ObjectMapper o = new ObjectMapper();
//		return o.writeValueAsString(entity);
//	}
//}
