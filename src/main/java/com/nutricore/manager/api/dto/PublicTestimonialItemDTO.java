package com.nutricore.manager.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PublicTestimonialItemDTO(
        @NotBlank @Size(max = 120) String name,
        @NotBlank @Size(max = 120) String label,
        @NotBlank @Size(max = 500) String quote,
        @NotBlank @Size(max = 255) String imageUrl
) {
}
