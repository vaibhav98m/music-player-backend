package com.spotify.music.player.config;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import com.spotify.music.player.annotation.SkipLogging;

public class PublicKeyUtil {

	@SkipLogging
	public static PublicKey loadPublicKey(String filePath) throws Exception {
		InputStream inputStream = PublicKeyUtil.class.getResourceAsStream(filePath);
		byte[] keyBytes = inputStream.readAllBytes();

		String publicKeyContent = new String(keyBytes).replace("-----BEGIN PUBLIC KEY-----", "")
				.replace("-----END PUBLIC KEY-----", "").replaceAll("\\s+", ""); // Remove newlines and spaces

		byte[] decodedKey = Base64.getDecoder().decode(publicKeyContent);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedKey);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(spec);
	}
}
