package com.spotify.music.player.config;

import java.nio.file.AccessDeniedException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.spotify.music.player.Exception.CustomExcetion;
import com.spotify.music.player.annotation.SkipLogging;

@Component
public class ClerkTokenValidator {

	@Value("${clerk.issuer-url}")
	private String clerkUrl;

	@Autowired
	private WebRequest webRequest;

	private final PublicKey publicKey;

	public ClerkTokenValidator(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	@SkipLogging
	public boolean validateToken(String token) throws TokenExpiredException, AccessDeniedException {

		Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, null);
		JWTVerifier verifier = JWT.require(algorithm).withIssuer(clerkUrl).build();

		verifier.verify(token);
		return true;

	}

	public String getUserId() {
		String authorizationHeader = webRequest.getHeader("Authorization");

		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			throw new CustomExcetion("Are bhai kyun kar rha hai tu", "025", HttpStatus.UNAUTHORIZED);
		}

		String token = authorizationHeader.substring(7);
		DecodedJWT jwt = JWT.decode(token);

		return jwt.getSubject();

	}
}
