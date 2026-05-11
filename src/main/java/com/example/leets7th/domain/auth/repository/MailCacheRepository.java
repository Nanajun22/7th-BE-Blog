package com.example.leets7th.domain.auth.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

//redis 접근용 클래스
@Service
@RequiredArgsConstructor
public class MailCacheRepository {

    private final StringRedisTemplate redisTemplate;

    private static final String AUTH_PREFIX = "EmailAuth:";

    private static final String VERIFY_PREFIX = "verify:";


    // 저장
    public void saveAuthCode(String email,String authCode) {
        String key = AUTH_PREFIX + email;
        redisTemplate.opsForValue().set(key,authCode, Duration.ofMinutes(5));
    }


    // 조회
    public String getAuthCode(String email) {
        String key = AUTH_PREFIX + email;
        return redisTemplate.opsForValue().get(key);
    }

    // 삭제
    public void deleteAuthCode(String email) {
        String key = AUTH_PREFIX + email;
        redisTemplate.delete(key);
    }

    // 인증 토큰 저장
    public void saveVerifyToken(String email,String token) {
        String key = AUTH_PREFIX+VERIFY_PREFIX+email;
        redisTemplate.opsForValue().set(key,token,Duration.ofMinutes(15));

    }

    //인증 토큰 조회
    public String getVerifyToken(String email) {
        String key = AUTH_PREFIX+VERIFY_PREFIX+email;
        return redisTemplate.opsForValue().get(key);
    }

    //인증 토큰 삭제
    public void deleteVerifyToken(String email) {
        String key = AUTH_PREFIX + VERIFY_PREFIX + email;
        redisTemplate.delete(key);
    }
}
