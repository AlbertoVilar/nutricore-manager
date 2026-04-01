package com.nutricore.manager.api.dto;

public record AuthenticatedUserResponseDTO(
        Long id,
        String fullName,
        String email,
        String role
) {
}
