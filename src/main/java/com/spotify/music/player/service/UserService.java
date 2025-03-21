package com.spotify.music.player.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spotify.music.player.model.StatusModel;
import com.spotify.music.player.model.UpdateModel;
import com.spotify.music.player.model.UserModel;

public interface UserService {
	List<UserModel> getAllUsers();

	UserModel getUserById();

	UserModel saveUser() throws JsonProcessingException;
	
	StatusModel updateUser(UpdateModel updateUser);
	

	void deleteUser(String id);
}