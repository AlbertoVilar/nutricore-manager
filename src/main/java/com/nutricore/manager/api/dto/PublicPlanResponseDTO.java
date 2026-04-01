package com.nutricore.manager.api.dto;

import java.util.List;

public record PublicPlanResponseDTO(
        Long id,
        String title,
        String subtitle,
        String priceLabel,
        String summary,
        List<String> features,
        Boolean featured,
        String ctaLabel
) {
}
