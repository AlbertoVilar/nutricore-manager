package com.nutricore.manager.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PublicPlanAdminRequestDTO(
        @NotBlank @Size(max = 80) String title,
        @NotBlank @Size(max = 120) String subtitle,
        @NotBlank @Size(max = 40) String priceLabel,
        @NotBlank @Size(max = 500) String summary,
        @NotEmpty List<@NotBlank @Size(max = 200) String> features,
        @NotNull Boolean featured,
        @NotNull Boolean active,
        @NotBlank @Size(max = 80) String ctaLabel,
        @NotBlank @Size(max = 255) String ctaUrl,
        @NotNull @Min(1) Integer displayOrder
) {
}
