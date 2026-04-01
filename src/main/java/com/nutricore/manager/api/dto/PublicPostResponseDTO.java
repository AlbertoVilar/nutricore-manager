package com.nutricore.manager.api.dto;

import java.time.LocalDate;

public record PublicPostResponseDTO(
        Long id,
        String title,
        String slug,
        String category,
        String excerpt,
        Integer readTimeMinutes,
        String imageUrl,
        LocalDate publishedAt
) {
}
