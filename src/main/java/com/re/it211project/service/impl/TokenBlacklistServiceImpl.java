package com.re.it211project.service.impl;

import com.re.it211project.service.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenBlacklistServiceImpl implements TokenBlacklistService {

    private final StringRedisTemplate stringRedisTemplate;

    private static final String PREFIX = "blacklist:";

    @Override
    public void blacklistToken(String token, Date expiration) {
        long ttl = expiration.getTime() - System.currentTimeMillis();

        if (ttl > 0) {
            stringRedisTemplate.opsForValue().set(
                    PREFIX + token,
                    "revoked",
                    Duration.ofMillis(ttl)
            );
        }
    }

    @Override
    public boolean isBlacklisted(String token) {
        return Boolean.TRUE.equals(
                stringRedisTemplate.hasKey(PREFIX + token)
        );
    }
}