package com.spotify.music.player.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClerkErrorResponse {
	@JsonProperty("errors")
	private List<ErrorDetail> errors;

	@JsonProperty("clerk_trace_id")
	private String clerkTraceId;
}