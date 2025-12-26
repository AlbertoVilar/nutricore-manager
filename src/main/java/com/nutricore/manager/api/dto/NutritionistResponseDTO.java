package com.nutricore.manager.api.dto;

public record NutritionistResponseDTO(
        Long id,
        String name,
        String email,
        String crn,
        String specialty) {
}
