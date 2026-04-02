package com.nutricore.manager.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PublicServicePillarItemDTO(
        @NotBlank @Size(max = 120) String title,
        @NotBlank @Size(max = 400) String description
) {
}
