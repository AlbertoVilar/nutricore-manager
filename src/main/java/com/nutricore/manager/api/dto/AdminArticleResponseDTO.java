package com.nutricore.manager.api.dto;

import com.nutricore.manager.domain.enums.editorial.EditorialStatus;

import java.time.LocalDateTime;
import java.util.List;

public record AdminArticleResponseDTO(
        Long id,
        String title,
        String slug,
        String summary,
        String body,
        String coverImageUrl,
        List<String> tags,
        String category,
        Integer readTimeMinutes,
        Boolean featured,
        EditorialStatus status,
        LocalDateTime publishedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
