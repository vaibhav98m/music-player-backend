package com.spotify.music.player.config;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.spotify.music.player.Exception.CustomExcetion;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class WebAuthInterceptor implements HandlerInterceptor {

	@Autowired
	private ClerkTokenValidator tokenValidator;

	@Override
	public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull Object handler) throws TokenExpiredException, AccessDeniedException, IOException {
		String authorizationHeader = request.getHeader("Authorization");

		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
			return false;
		}

		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			throw new CustomExcetion("Are bhai kyun kar rha hai tu", "025", HttpStatus.UNAUTHORIZED);

		}

		String token = authorizationHeader.substring(7);
		if (!tokenValidator.validateToken(token)) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}

		return true;
	}

}
