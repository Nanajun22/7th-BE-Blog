package com.example.leets7th.global.util;

import com.example.leets7th.domain.user.domain.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    // JWT 액세스 토큰 생성
    public String generateAccessToken(Long userId, UserRole role) {
        return Jwts.builder()
                .subject(userId.toString())
                .claim("role",role.name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*15))
                .signWith(getSecretKey())
                .compact();

    }

    public String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }



    // SHA256 알고리즘 사용할 수 있는 바이트 형태로 변환
    private SecretKey getSecretKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //토큰 검증
    public boolean isValidToken(String token) {
        try {
            parseClaims(token);
            return true;
        }
        catch (ExpiredJwtException ex) {
            throw ex;
        }
        catch (JwtException ex) {
            return false;
        }
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUserId(String token) {
        return parseClaims(token).getSubject();
    }

    public String getRole(String token) {
        return parseClaims(token).get("role",String.class);
    }


}
