package com.nutricore.manager.api.dto;

import java.time.LocalDateTime;
import java.util.List;

public record PublicPlanAdminResponseDTO(
        Long id,
        String title,
        String subtitle,
        String priceLabel,
        String summary,
        List<String> features,
        Boolean featured,
        Boolean active,
        String ctaLabel,
        String ctaUrl,
        Integer displayOrder,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
