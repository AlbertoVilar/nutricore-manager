package com.nutricore.manager.api.dto;

import java.util.List;

public record PublicRecipeResponseDTO(
        Long id,
        String title,
        String slug,
        String summary,
        String imageUrl,
        Integer prepTimeMinutes,
        String caloriesLabel,
        List<String> ingredients,
        List<String> preparationSteps
) {
}
