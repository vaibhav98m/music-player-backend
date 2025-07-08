package com.spotify.music.player.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Document(collection = "Users")
@Getter
@Setter
public class UserEntity {

	@Id
	private String id;

	private String username;

	private String firstName;

	private String lastName;

	private String email;

	private Date createdAt;
	private Date updatedAt;

	// Constructors, getters, setters, equals, and hashCode methods
	public UserEntity() {
	}

//	public UserEntity(String username, String email) {
//		this.username = username;
//		this.email = email;
//	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserEntity userEntity = (UserEntity) o;
		return Objects.equals(id, userEntity.id) && Objects.equals(username, userEntity.username)
				&& Objects.equals(email, userEntity.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, email);
	}
}