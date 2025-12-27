package com.nutricore.manager.api.dto;

import com.nutricore.manager.domain.enums.goal.GoalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.LocalDate;

public record NutritionGoalRequestDTO(

        @NotNull(message = "O ID do paciente é obrigatório")
        Long patientId,

        @NotNull(message = "O tipo do objetivo é obrigatório")
        GoalType goalType,

        @Positive(message = "O peso alvo deve ser um valor positivo")
        BigDecimal targetWeight,

        @Positive(message = "O percentual de gordura alvo deve ser positivo")
        BigDecimal targetBodyFatPercentage,

        @Positive(message = "A massa magra alvo deve ser positiva")
        BigDecimal targetLeanMass,

        @NotNull(message = "A data de início é obrigatória")
        @PastOrPresent(message = "A data de início não pode ser futura")
        LocalDate startDate,

        LocalDate expectedEndDate,

        String notes
) {
}

