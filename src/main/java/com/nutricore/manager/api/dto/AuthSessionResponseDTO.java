package com.nutricore.manager.api.dto;

public record AuthSessionResponseDTO(
        String accessToken,
        String tokenType,
        String expiresAt,
        AuthenticatedUserResponseDTO user
) {
}
