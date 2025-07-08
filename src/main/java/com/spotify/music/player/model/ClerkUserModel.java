package com.spotify.music.player.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClerkUserModel {

	private String id;
	private String object;
	
	private String username;
	@JsonAlias("first_name")
	private String firstName;
	@JsonAlias("last_name")
	private String lastName;
	@JsonAlias("image_url")
	private String imageUrl;
	@JsonAlias("has_image")
	private boolean hasImage;
	@JsonAlias("email_addresses")
	private List<EmailAddress> emailAddresses;
	@JsonAlias("last_sign_in_at")
	private long lastSignInAt;
	@JsonAlias("created_at")
	private long createdAt;
	@JsonAlias("updated_at")
	private long updatedAt;
	@JsonAlias("profile_image_url")
	private String profileImageUrl;

	

}
