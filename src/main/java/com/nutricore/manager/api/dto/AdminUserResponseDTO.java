package com.nutricore.manager.api.dto;

import java.time.LocalDateTime;

public record AdminUserResponseDTO(
        Long id,
        String fullName,
        String email,
        String role,
        Boolean active,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
