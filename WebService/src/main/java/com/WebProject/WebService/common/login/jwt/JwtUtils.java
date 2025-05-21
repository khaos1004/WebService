package com.WebProject.WebService.common.login.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secretKeyPlain;

    private Key secretKey;

    private static final long ACCESS_TOKEN_VALIDITY = 1000 * 60 * 20; // 20분
    private static final long REFRESH_TOKEN_VALIDITY = 1000 * 60 * 60 * 2; // 2시간

    @PostConstruct
    protected void init() {
        this.secretKey = Keys.hmacShaKeyFor(secretKeyPlain.getBytes());
    }

    // ✅ AccessToken 생성
    public String createAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
//                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ RefreshToken 생성
    public String createRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // ✅ Claims 추출
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getEmail(String token) {
        return getClaims(token).getSubject(); // subject에 email 저장한 거니까 그대로 꺼냄
    }

}
