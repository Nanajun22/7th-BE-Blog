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
    public void saveRefreshToken(String token,Long userId) {
        String key = RT_PREFIX + token;
        redisTemplate.opsForValue().set(key,userId.toString(), Duration.ofDays(7));
    }

    public Long getUserIdByRefreshToken(String token) {
        String key = RT_PREFIX + token;

        String userId = redisTemplate.opsForValue().get(key);

        if(userId == null) {
            return null;
        }
        return Long.valueOf(userId);
    }


    public void deleteRefreshToken(String token) {
        String key = RT_PREFIX + token;
        redisTemplate.delete(key);
    }
}
