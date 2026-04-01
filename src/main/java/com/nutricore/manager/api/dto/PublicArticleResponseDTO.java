package com.nutricore.manager.api.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PublicArticleResponseDTO(
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
        LocalDateTime publishedAt
) {
}
