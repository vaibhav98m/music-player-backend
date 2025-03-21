package com.spotify.music.player.service;

import com.spotify.music.player.model.TextToSpeechRequestModel;

public interface ElevenLabsService {
	public String textToSpeechByElevenLabs(TextToSpeechRequestModel request, String viceId, String outputFormat);
}
