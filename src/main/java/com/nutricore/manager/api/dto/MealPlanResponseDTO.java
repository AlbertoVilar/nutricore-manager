package com.nutricore.manager.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record MealPlanResponseDTO(
        UUID id,
        Long patientId,
        Long nutritionGoalId,
        BigDecimal totalCalories,
        Boolean active,
        LocalDateTime createdAt) {
}
