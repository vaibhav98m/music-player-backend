package com.spotify.music.player.service;

import java.util.List;

import com.spotify.music.player.model.SongByGenreDetails;
import com.spotify.music.player.model.SongByYearDetails;
import com.spotify.music.player.model.SongDetails;

public interface SongsService {

	SongDetails getSongDetailsById(Integer id);

	List<SongDetails> getAllSongDetails(Integer limit, Integer offset);

	List<String> getAllYears();

	SongByYearDetails getSongByYears(String year, Integer limit, Integer offset);

	List<String> getAvailableGenre();

	SongByGenreDetails getSongsByGenre(String genre,Integer limit, Integer offset);

}
