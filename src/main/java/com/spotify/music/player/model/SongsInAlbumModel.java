package com.spotify.music.player.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SongsInAlbumModel {

	private String albumId;
	private String albumName;
	private String image;
	private List<SongDetails> songs;

}
