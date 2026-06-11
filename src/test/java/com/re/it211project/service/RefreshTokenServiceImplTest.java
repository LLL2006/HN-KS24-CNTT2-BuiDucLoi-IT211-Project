package com.re.it211project.service;

import com.re.it211project.entity.RefreshToken;
import com.re.it211project.repository.RefreshTokenRepository;
import com.re.it211project.service.impl.RefreshTokenServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceImplTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private RefreshTokenServiceImpl refreshTokenService;

    @Test
    void verifyRefreshToken_success() {
        RefreshToken token = RefreshToken.builder()
                .token("refresh-token")
                .expiryDate(LocalDateTime.now().plusDays(1))
                .build();

        when(refreshTokenRepository.findByToken("refresh-token"))
                .thenReturn(Optional.of(token));

        RefreshToken result = refreshTokenService.verifyRefreshToken("refresh-token");

        assertEquals("refresh-token", result.getToken());
    }
}