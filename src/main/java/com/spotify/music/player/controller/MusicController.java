package com.spotify.music.player.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spotify.music.player.model.SongByGenreDetails;
import com.spotify.music.player.model.SongByYearDetails;
import com.spotify.music.player.model.SongDetails;
import com.spotify.music.player.service.SongsService;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/music")
public class MusicController {

	@Autowired
	SongsService songsService;

	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SongDetails.class)))})
	@GetMapping("/song/{id}")
	public ResponseEntity<SongDetails> getSongsById(@PathVariable("id") Integer id,
			@RequestHeader(name = "uniqueId", required = true, defaultValue = "test-123") String uniqueId) {
		SongDetails songs = songsService.getSongDetailsById(id);
		return new ResponseEntity<>(songs, HttpStatus.OK);
	}

	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema( schema = @Schema(implementation = SongDetails.class)))) })
	@GetMapping("/songs")
	public ResponseEntity<List<SongDetails>> getAllSongs(
			@RequestHeader(name = "uniqueId", required = true, defaultValue = "test-123") String uniqueId,
			@RequestHeader(name = "limit", required = false) Integer limit,
			@RequestHeader(name = "offset", defaultValue = "1", required = false) Integer offset) {

		List<SongDetails> songsList = songsService.getAllSongDetails(limit, offset);
		return new ResponseEntity<>(songsList, HttpStatus.OK);
	}
	
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema( schema = @Schema(implementation = String.class)))) })
	@GetMapping("/year")
	public ResponseEntity<List<String>> getAllYears(
			@RequestHeader(name = "uniqueId", required = true, defaultValue = "test-123") String uniqueId) {

		List<String> yearList = songsService.getAllYears();
		return new ResponseEntity<>(yearList, HttpStatus.OK);
	}
	
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SongByYearDetails.class))) })
	@GetMapping("/{year}/year")
	public ResponseEntity<SongByYearDetails> getAllSongsByYear(
			@RequestHeader(name = "uniqueId", required = true, defaultValue = "test-123") String uniqueId,
			@PathVariable(name="year", required = true)String year,
			@RequestHeader(name = "limit", required = false) Integer limit,
			@RequestHeader(name = "offset", defaultValue = "1", required = false) Integer offset) {

		SongByYearDetails songsByYear = songsService.getSongByYears(year, limit, offset);
		return new ResponseEntity<>(songsByYear, HttpStatus.OK);
	}
	
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema( schema = @Schema(implementation = String.class)))) })
	@GetMapping("/genre")
	public ResponseEntity<List<String>> getAvailableGenre(
			@RequestHeader(name = "uniqueId", required = true, defaultValue = "test-123") String uniqueId) {

		List<String> genre = songsService.getAvailableGenre();
		return new ResponseEntity<>(genre, HttpStatus.OK);
	}

	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,  schema = @Schema(implementation = SongByGenreDetails.class))) })
	@GetMapping("/{genre}/genre")
	public ResponseEntity<SongByGenreDetails> getSongsByGenre(
			@RequestHeader(name = "uniqueId", required = true, defaultValue = "test-123") String uniqueId,
			@PathVariable(name="genre", required = true)String genre,
			@RequestHeader(name = "limit", required = false) Integer limit,
			@RequestHeader(name = "offset", defaultValue = "1", required = false) Integer offset) {

		SongByGenreDetails genres = songsService.getSongsByGenre(genre, limit, offset);
		return new ResponseEntity<>(genres, HttpStatus.OK);
	}

}
