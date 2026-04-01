package com.nutricore.manager.api.dto;

import com.nutricore.manager.domain.enums.editorial.EditorialStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public record AdminArticleUpsertRequestDTO(
        @NotBlank @Size(max = 160) String title,
        @Size(max = 180) String slug,
        @NotBlank @Size(max = 500) String summary,
        @NotBlank String body,
        @Size(max = 255) String coverImageUrl,
        List<@Size(max = 50) String> tags,
        @Size(max = 80) String category,
        @Positive Integer readTimeMinutes,
        Boolean featured,
        EditorialStatus status,
        LocalDateTime publishedAt
) {
}
