package com.spotify.music.player.utility;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.music.player.Exception.CustomExcetion;
import com.spotify.music.player.entity.UserEntity;
import com.spotify.music.player.model.ClerkErrorResponse;
import com.spotify.music.player.model.ClerkUserModel;
import com.spotify.music.player.repository.UserRepository;

@Component
public class UserUtility {

	@Value("${clerk-private-api-key}")
	private String clerkPrivateKey;

	@Value("${get-user-clerk}")
	private String getUserUrl;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	@Qualifier("BoomBoxRestTemplate")
	private RestTemplate restTemplate;

	public boolean validateUser(String userId) throws JsonProcessingException {
		ClerkUserModel clerkUser = getClerkUserDetails(userId);

		UserEntity userEntity = null;

		if (clerkUser != null && !clerkUser.getEmailAddresses().get(0).getEmailAddress().isEmpty()
				&& !clerkUser.getUsername().isEmpty()) {
			userEntity = getDatabaseUserDetails(userId, clerkUser.getEmailAddresses().get(0).getEmailAddress(),
					clerkUser.getUsername());
		}

		return UserComparator.compareUserModelAndEntity(clerkUser, userEntity);

	}

	public ClerkUserModel getClerkUserDetails(String userId) throws JsonProcessingException {
		ClerkUserModel clerkUser = null;

		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(clerkPrivateKey);

		ObjectMapper mapper = new ObjectMapper();

		ResponseEntity<String> response = null;

		HttpEntity<String> entity = new HttpEntity<>(headers);
		try {
			response = restTemplate.exchange(getUserUrl + userId, HttpMethod.GET, entity, String.class);

			if (response.getStatusCode().is2xxSuccessful())
				clerkUser = mapper.readValue(response.getBody(), ClerkUserModel.class);
		} catch (HttpClientErrorException ex) {
			ClerkErrorResponse error = mapper.readValue(ex.getResponseBodyAsString(), ClerkErrorResponse.class);
			throw new CustomExcetion(error.getErrors().get(0).getLongMessage(), "CL01", ex.getStatusCode());
		}

		return clerkUser;
	}

	private UserEntity getDatabaseUserDetails(String userId, String email, String username) {
		Optional<UserEntity> entity = userRepository.findById(userId);
		Optional<UserEntity> mailEntity = userRepository.findByEmail(email);
		Optional<UserEntity> userNameEntity = userRepository.findByUsername(username);

		return entity.isPresent() ? entity.get()
				: mailEntity.isPresent() ? mailEntity.get() : userNameEntity.isPresent() ? userNameEntity.get() : null;
	}

}
