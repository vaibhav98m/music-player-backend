package com.spotify.music.player.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spotify.music.player.model.StatusModel;
import com.spotify.music.player.model.UpdateModel;
import com.spotify.music.player.model.UserModel;
import com.spotify.music.player.service.UserService;



@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userServiceImpl;

	@GetMapping("/all")
	public List<UserModel> getAllUsers() {
		return userServiceImpl.getAllUsers();
	}

	@GetMapping("/user")
	public ResponseEntity<UserModel> getUserById() {
		UserModel user = userServiceImpl.getUserById();
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}

	@GetMapping("/save")
	public ResponseEntity<UserModel> createUser() throws JsonProcessingException {
		UserModel model = userServiceImpl.saveUser();
		if (model.getStatus().equalsIgnoreCase("0"))
			return new ResponseEntity<>(model, HttpStatus.CREATED);
		else {
			return new ResponseEntity<>(model, HttpStatus.OK);
		}
	}

    @PutMapping(path  = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatusModel> updateUser(@RequestBody UpdateModel updatedUser) {
    	StatusModel user = userServiceImpl.updateUser(updatedUser);
		return new ResponseEntity<>(user, HttpStatus.OK);
    }

	@DeleteMapping("/delete")
	public BodyBuilder deleteUser(@PathVariable String id) {
		userServiceImpl.deleteUser(id);
		ResponseEntity.noContent().build();
		return ResponseEntity.status(HttpStatusCode.valueOf(204));
	}
}