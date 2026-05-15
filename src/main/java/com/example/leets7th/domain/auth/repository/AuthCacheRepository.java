package com.example.leets7th.domain.auth.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class AuthCacheRepository {
    private final StringRedisTemplate redisTemplate;

    private static final String RT_PREFIX = "RT:";


    //
    public void saveRefreshToken(String token,String loginId) {
        String key = RT_PREFIX + token;
        redisTemplate.opsForValue().set(key,loginId, Duration.ofDays(7));
    }

    public String getRefreshToken(String token) {
        String key = RT_PREFIX + token;
        return redisTemplate.opsForValue().get(key);
    }


    public void deleteRefreshToken(String token) {
        String key = RT_PREFIX + token;
        redisTemplate.delete(key);
    }
}
