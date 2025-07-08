package com.spotify.music.player.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlbumModel {

	private String albumId;
	private String albumName;
	private String albumFullName;
	private String image;

}
