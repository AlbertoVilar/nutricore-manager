package com.nutricore.manager.api.dto;

import com.nutricore.manager.domain.enums.editorial.EditorialStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public record AdminRecipeUpsertRequestDTO(
        @NotBlank @Size(max = 160) String title,
        @Size(max = 180) String slug,
        @NotBlank @Size(max = 500) String description,
        @Size(max = 255) String imageUrl,
        @NotNull List<@NotBlank String> ingredients,
        @NotNull List<@NotBlank String> preparationSteps,
        @NotNull @Positive Integer prepTimeMinutes,
        @NotBlank @Size(max = 80) String yieldInfo,
        @Size(max = 80) String category,
        Boolean featured,
        EditorialStatus status,
        LocalDateTime publishedAt
) {
}
