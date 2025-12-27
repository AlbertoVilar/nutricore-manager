package com.nutricore.manager.api.dto;

import com.nutricore.manager.domain.enums.goal.GoalStatus;
import com.nutricore.manager.domain.enums.goal.GoalType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record NutritionGoalResponseDTO(

        Long id,
        Long patientId,

        GoalType goalType,
        GoalStatus status,

        BigDecimal targetWeight,
        BigDecimal targetBodyFatPercentage,
        BigDecimal targetLeanMass,

        LocalDate startDate,
        LocalDate expectedEndDate,

        Long overdueDays,

        String notes,

        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

