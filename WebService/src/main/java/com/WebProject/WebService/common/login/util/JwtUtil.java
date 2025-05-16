package com.WebProject.WebService.common.login.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String accessTokenSecret = "access-secret-key-1234567890";
    private final String refreshTokenSecret = "refresh-secret-key-0987654321";

    private final long accessTokenExpiryMs = 1000 * 60 * 30; // 30��
    private final long refreshTokenExpiryMs = 1000L * 60 * 60 * 24 * 7; // 7��

    public String generateAccessToken(String username) {
        return generateToken(username, accessTokenExpiryMs, accessTokenSecret);
    }

    public String generateRefreshToken(String username) {
        return generateToken(username, refreshTokenExpiryMs, refreshTokenSecret);
    }

    private String generateToken(String username, long expiryMs, String secret) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiryMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String validateAccessTokenAndGetUsername(String token) {
        return validateTokenAndGetUsername(token, accessTokenSecret);
    }

    public String validateRefreshTokenAndGetUsername(String token) {
        return validateTokenAndGetUsername(token, refreshTokenSecret);
    }

    private String validateTokenAndGetUsername(String token, String secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
