package com.re.it211project.service;

import com.re.it211project.service.impl.TokenBlacklistServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenBlacklistServiceImplTest {

    @Mock
    private StringRedisTemplate stringRedisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private TokenBlacklistServiceImpl tokenBlacklistService;

    @Test
    void blacklistToken_success() {
        when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);

        tokenBlacklistService.blacklistToken(
                "access-token",
                new Date(System.currentTimeMillis() + 60000)
        );

        verify(valueOperations).set(
                startsWith("blacklist:"),
                eq("revoked"),
                any()
        );
    }

    @Test
    void isBlacklisted_success() {
        when(stringRedisTemplate.hasKey("blacklist:access-token"))
                .thenReturn(true);

        boolean result = tokenBlacklistService.isBlacklisted("access-token");

        assertTrue(result);
    }
}