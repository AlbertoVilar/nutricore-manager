package com.nutricore.manager.api.dto;

import com.nutricore.manager.domain.enums.editorial.EditorialStatus;

import java.time.LocalDateTime;
import java.util.List;

public record AdminRecipeResponseDTO(
        Long id,
        String title,
        String slug,
        String description,
        String imageUrl,
        List<String> ingredients,
        List<String> preparationSteps,
        Integer prepTimeMinutes,
        String yieldInfo,
        String category,
        Boolean featured,
        EditorialStatus status,
        LocalDateTime publishedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
