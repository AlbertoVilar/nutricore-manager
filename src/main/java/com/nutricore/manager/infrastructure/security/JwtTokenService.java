package com.nutricore.manager.infrastructure.security;

import com.nutricore.manager.domain.entities.UserAccount;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final JwtProperties jwtProperties;

    private SecretKey signingKey;

    @PostConstruct
    public void initialize() {
        if (jwtProperties.getSecret() == null || jwtProperties.getSecret().isBlank()) {
            throw new IllegalStateException("JWT secret nao configurado para este ambiente.");
        }

        byte[] keyBytes = jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8);
        if (keyBytes.length < 32) {
            throw new IllegalStateException("JWT secret deve possuir pelo menos 32 bytes.");
        }

        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(UserAccount userAccount, OffsetDateTime expiresAt) {
        Instant issuedAt = Instant.now();

        return Jwts.builder()
                .issuer(jwtProperties.getIssuer())
                .subject(userAccount.getEmail())
                .claim("uid", userAccount.getId())
                .claim("role", userAccount.getRole().name())
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiresAt.toInstant()))
                .signWith(signingKey)
                .compact();
    }

    public OffsetDateTime calculateExpiry() {
        return OffsetDateTime.now(ZoneOffset.UTC).plus(jwtProperties.getAccessTokenTtl());
    }

    public String extractSubject(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, String expectedSubject) {
        Claims claims = parseClaims(token);
        return claims.getSubject().equalsIgnoreCase(expectedSubject)
                && claims.getExpiration().toInstant().isAfter(Instant.now());
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
