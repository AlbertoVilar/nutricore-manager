package com.nutricore.manager.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PublicSiteMetricItemDTO(
        @NotBlank @Size(max = 80) String title,
        @NotBlank @Size(max = 60) String value,
        @NotBlank @Size(max = 240) String description
) {
}
