package com.spotify.music.player.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SongDetails {
	
	private Integer id;
	private String title;
	private String album;
	private String artist;
	private String duration;
	private String year;
	private String genre;
	private String filePath;
	private String imageIcon;

}
