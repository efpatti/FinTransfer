package com.fintransfer.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.security.Key;

@Component
public class JwtUtil {
	private final Key SECRET_KEY = Keys.hmacShaKeyFor("your_super_secret_key_which_is_long_enough_for_hmac_sha256".getBytes());
	private final long EXPIRATION_MS = 86400000; // 1 day

	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
				.signWith(SECRET_KEY, SignatureAlgorithm.HS256)
				.compact();
	}

	public String extractUsername(String token) {
		return getClaims(token).getSubject();
	}

	public boolean validateToken(String token, String username) {
		final String extractedUsername = extractUsername(token);
		return (extractedUsername.equals(username) && !isTokenExpired(token));
	}

	private Claims getClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(SECRET_KEY)
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	private boolean isTokenExpired(String token) {
		return getClaims(token).getExpiration().before(new Date());
	}
}
