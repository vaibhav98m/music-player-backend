package com.spotify.music.player.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "Albums")
@Getter
@Setter
public class AlbumEntity {
	@Id
	private Integer id;
	private String albumId;
	private String albumName;
	private Integer songId;
	private String image;
}
