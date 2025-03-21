//package com.spotify.music.player.controller;
//
//import org.springdoc.core.annotations.ParameterObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.spotify.music.player.model.SongDetails;
//import com.spotify.music.player.model.TextToSpeechRequestModel;
//import com.spotify.music.player.service.ElevenLabsService;
//
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//
//@RestController
//@RequestMapping("/eleveln-labs")
//public class ElevenLabsController {
//
//	@Autowired
//	ElevenLabsService elevenLabsService;
//
//	@ApiResponses(value = {
//			@ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = SongDetails.class))) })
//	@GetMapping("/text-to-speech/{voiceId}")
//	public ResponseEntity<String> textToSpeechByElevenLabs(
//			@RequestBody TextToSpeechRequestModel textToSpeechRequestModel, @PathVariable("voiceId") String voiceId,
//			@ParameterObject String output,
//			@RequestHeader(name = "uniqueId", required = true, defaultValue = "test-123") String uniqueId) {
//		String speech = elevenLabsService.textToSpeechByElevenLabs(textToSpeechRequestModel, voiceId, output);
//		return new ResponseEntity<>(speech, HttpStatus.OK);
//	}
//
//}
