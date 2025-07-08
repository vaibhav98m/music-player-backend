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

import com.spotify.music.player.model.AlbumModel;
import com.spotify.music.player.model.SongsInAlbumModel;
import com.spotify.music.player.service.AlbumsService;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/album")
public class AlbumController {

	@Autowired
	AlbumsService albumsService;

	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SongsInAlbumModel.class))) })
	@GetMapping("/detail/{albumId}")
	public ResponseEntity<SongsInAlbumModel> getSongsByAlbumId(@PathVariable("albumId") String albumId,
			@RequestHeader(name = "uniqueId", required = true, defaultValue = "test-123") String uniqueId) {
		SongsInAlbumModel albumModel = albumsService.getSongsByAlbumId(albumId);
		return new ResponseEntity<>(albumModel, HttpStatus.OK);
	}
 
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema( schema = @Schema(implementation = AlbumModel.class)))) })
	@GetMapping("/detail/all")
	public ResponseEntity<List<AlbumModel>> getAllAlbums(
			@RequestHeader(name = "uniqueId", required = true, defaultValue = "test-123") String uniqueId,
			@RequestHeader(name = "limit", defaultValue = "64", required = false) Integer limit,
			@RequestHeader(name = "offset", defaultValue = "1", required = false) Integer offset) {

		List<AlbumModel> albumList = albumsService.getAllAlbums(limit, offset);
		return new ResponseEntity<>(albumList, HttpStatus.OK);
	}

}
