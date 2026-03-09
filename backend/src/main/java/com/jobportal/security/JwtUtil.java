package com.jobportal.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET =
            "my-super-secret-key-my-super-secret-key-my-super-secret-key";

    // Access token → 15 minutes
    private static final long ACCESS_EXPIRATION = 1000 * 60 * 15;

    // Refresh token → 7 days
    private static final long REFRESH_EXPIRATION = 1000L * 60 * 60 * 24 * 7;

    // ==============================
    // Get Signing Key
    // ==============================
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    // ==============================
    // Generate Access Token
    // ==============================
    public String generateAccessToken(String email) {

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ==============================
    // Generate Refresh Token
    // ==============================
    public String generateRefreshToken(String email) {

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ==============================
    // Extract Email
    // ==============================
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    // ==============================
    // Extract Expiration
    // ==============================
    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    // ==============================
    // Extract Claims
    // ==============================
    private Claims extractClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ==============================
    // Check Expiration
    // ==============================
    private boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    // ==============================
    // Validate Token with Email
    // ==============================
    public boolean validateToken(String token, String email) {

        final String extractedEmail = extractEmail(token);

        return extractedEmail.equals(email) && !isTokenExpired(token);
    }

    // ==============================
    // Basic Validation
    // ==============================
    public boolean validateToken(String token) {

        try {

            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);

            return true;

        } catch (JwtException | IllegalArgumentException e) {

            return false;
        }
    }
}