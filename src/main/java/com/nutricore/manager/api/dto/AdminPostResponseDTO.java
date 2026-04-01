package com.nutricore.manager.api.dto;

import com.nutricore.manager.domain.enums.editorial.EditorialStatus;

import java.time.LocalDateTime;
import java.util.List;

public record AdminPostResponseDTO(
        Long id,
        String title,
        String slug,
        String summary,
        String body,
        String coverImageUrl,
        List<String> galleryImageUrls,
        String videoUrl,
        String caption,
        String category,
        Boolean featured,
        EditorialStatus status,
        LocalDateTime publishedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
