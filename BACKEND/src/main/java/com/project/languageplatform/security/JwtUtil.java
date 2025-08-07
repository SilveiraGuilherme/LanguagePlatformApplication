package com.project.languageplatform.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Utility class for generating, parsing, and validating JWT tokens.
 * Used for securing user authentication and authorization.
 */
@Component
public class JwtUtil {

    // Static secret key used for signing JWTs (replace with secure environment variable in production)
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("secretKey1234567890secretKey1234567890".getBytes());

    // JWT token validity duration (10 hours)
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

    // Generate a JWT token using user's email and role
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract email (subject) from token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // Extract user role from token claims
    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    // Validate token: check if it matches the user's email and is not expired
    public boolean validateToken(String token, String userEmail) {
        final String username = extractUsername(token);
        return username.equals(userEmail) && !isTokenExpired(token);
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Extract claims from token
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}