package com.spotify.music.player.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "Songs")
@Getter
@Setter
public class SongEntity {
	@Id
	private Integer id;
	private String title;
	private String album;
	private String artist;
	private String duration;
	@Field(name = "file_path")
	private String filePath;
	@Field(name="image")
	private String imageIcon;
	private String year;
	private String genre;
}
