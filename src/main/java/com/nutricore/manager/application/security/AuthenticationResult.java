package com.nutricore.manager.application.security;

import java.time.OffsetDateTime;

public record AuthenticationResult(
        String accessToken,
        OffsetDateTime expiresAt,
        AuthenticatedUserSnapshot user
) {
}
