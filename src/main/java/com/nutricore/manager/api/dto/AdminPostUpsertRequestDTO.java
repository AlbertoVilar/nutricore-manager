package com.nutricore.manager.api.dto;

import com.nutricore.manager.domain.enums.editorial.EditorialStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public record AdminPostUpsertRequestDTO(
        @NotBlank @Size(max = 160) String title,
        @Size(max = 180) String slug,
        @Size(max = 500) String summary,
        @NotBlank String body,
        @Size(max = 255) String coverImageUrl,
        List<@Size(max = 255) String> galleryImageUrls,
        @Size(max = 255) String videoUrl,
        @Size(max = 255) String caption,
        @Size(max = 80) String category,
        Boolean featured,
        EditorialStatus status,
        LocalDateTime publishedAt
) {
}
