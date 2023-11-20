package com.socaly.refreshToken;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken() {
        final RefreshToken refreshToken = new RefreshToken();

        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }

    public void validateRefreshToken(final String refreshToken) {
        refreshTokenRepository.findByToken(refreshToken).orElseThrow(
                () -> new RefreshTokenException("Invalid refresh token")
        );
    }

    public void deleteRefreshToken(final String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
