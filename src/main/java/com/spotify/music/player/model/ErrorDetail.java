package com.spotify.music.player.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetail {
    @JsonProperty("message")
    private String message;

    @JsonProperty("long_message")
    private String longMessage;

    @JsonProperty("code")
    private String code;
}