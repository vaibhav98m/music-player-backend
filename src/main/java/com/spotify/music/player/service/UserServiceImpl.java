package com.spotify.music.player.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.music.player.config.ClerkTokenValidator;
import com.spotify.music.player.entity.UserEntity;
import com.spotify.music.player.model.ClerkUserModel;
import com.spotify.music.player.model.StatusModel;
import com.spotify.music.player.model.UpdateModel;
import com.spotify.music.player.model.UserModel;
import com.spotify.music.player.repository.UserRepository;
import com.spotify.music.player.utility.UserUtility;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserUtility userUtility;

	@Autowired
	private ClerkTokenValidator clerkTokenValidator;

	public List<UserModel> getAllUsers() {
		List<UserModel> users = new ArrayList<>();
		List<UserEntity> usersEntity = userRepository.findAll();

		if (usersEntity.isEmpty())
			return null;

		for (UserEntity userEntity : usersEntity) {
			UserModel user = new UserModel();
			user.setEmail(userEntity.getEmail());
			user.setFirstName(userEntity.getFirstName());
			user.setLastName(userEntity.getLastName());
			user.setUserId(userEntity.getId());
			user.setUsername(userEntity.getUsername());
			users.add(user);
		}

		return users;
	}

	public UserModel getUserById() {
		String id = clerkTokenValidator.getUserId();
		Optional<UserEntity> userEntity = userRepository.findById(id);
		UserModel user = null;

		if (!userEntity.isPresent())
			return user;
		user = new UserModel();
		user.setEmail(userEntity.get().getEmail());
		user.setFirstName(userEntity.get().getFirstName());
		user.setLastName(userEntity.get().getLastName());
		user.setUserId(userEntity.get().getId());
		user.setUsername(userEntity.get().getUsername());
		return user;
	}

	@Override
	public UserModel saveUser() throws JsonProcessingException {
		String userId = clerkTokenValidator.getUserId();
		UserEntity userEntity = new UserEntity();
		UserModel model = new UserModel();
		boolean validate = userUtility.validateUser(userId);

		if (validate) {
			model.setStatus("1");
			model.setMessage("User already exist");
			return model;
		}

		ClerkUserModel clerkUser = userUtility.getClerkUserDetails(userId);

		userEntity.setId(userId);
		userEntity.setEmail(clerkUser.getEmailAddresses().get(0).getEmailAddress());
		userEntity.setFirstName(clerkUser.getFirstName());
		userEntity.setLastName(clerkUser.getLastName());
		userEntity.setUsername(clerkUser.getUsername());
		userEntity.setCreatedAt(new Date(clerkUser.getCreatedAt()));
		userEntity.setUpdatedAt(new Date(clerkUser.getUpdatedAt()));

		userRepository.save(userEntity);

		ObjectMapper mapper = new ObjectMapper();

		model = mapper.readValue(mapper.writeValueAsString(userEntity), UserModel.class);
		model.setStatus("0");
		model.setMessage("User Created Successfully");

		return model;
	}

	@Override
	public void deleteUser(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public StatusModel updateUser(UpdateModel updatedUser) {
		String id = clerkTokenValidator.getUserId();
		Optional<UserEntity> userEntity = userRepository.findById(id);

		if (!userEntity.isPresent())
			return new StatusModel("2", "User does not exist");

		userEntity.get().setUsername(updatedUser.getUsername());
		userEntity.get().setFirstName(updatedUser.getFirstName());
		userEntity.get().setLastName(updatedUser.getLastName());
		userEntity.get().setUpdatedAt(new Date());

		if (!userEntity.isPresent())
			return new StatusModel("2", "User does not exist");

		userRepository.save(userEntity.get());
		return new StatusModel("0", "User Updated Successfully");
	}

}