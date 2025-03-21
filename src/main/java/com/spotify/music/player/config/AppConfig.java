package com.spotify.music.player.config;

import java.security.PublicKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.spotify.music.player.annotation.SkipLogging;

@Configuration
public class AppConfig {
	
	public enum keys{
		WEATHER_API;
	}

	@Bean
	@SkipLogging
	PublicKey clerkPublicKey() throws Exception {
		return PublicKeyUtil.loadPublicKey("/public_key_clerk.pem");
	}

	@Bean
	@SkipLogging
	ClerkTokenValidator clerkTokenValidator(PublicKey clerkPublicKey) {
		return new ClerkTokenValidator(clerkPublicKey);
	}

	@Bean("BoomBoxRestTemplate")
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
