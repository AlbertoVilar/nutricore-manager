package com.nutricore.manager.api.dto;

import com.nutricore.manager.domain.enums.goal.ActivityLevel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDate;

public record AnthropometricRequestDTO(

                @NotNull(message = "O ID do paciente é obrigatório") Long patientId,

                @NotNull(message = "A data da avaliação é obrigatória") @PastOrPresent(message = "A data não pode ser no futuro") LocalDate assessmentDate,

                // --- Antropometria Básica ---
                @NotNull(message = "O peso é obrigatório") @Positive(message = "O peso deve ser maior que zero") BigDecimal weight,

                @NotNull(message = "A altura é obrigatória") @Positive(message = "A altura deve ser maior que zero") BigDecimal height,

                ActivityLevel activityLevel, // Nível de atividade física

                // --- Composição corporal (Podem ser zero ou nulos) ---
                @PositiveOrZero BigDecimal bodyFatPercentage,
                @PositiveOrZero BigDecimal muscleMassKg,
                @PositiveOrZero BigDecimal totalBodyWater,
                @PositiveOrZero BigDecimal visceralFat,
                @PositiveOrZero BigDecimal basalMetabolicRate,

                // --- Perímetros (Podem ser zero se não medidos) ---
                @PositiveOrZero BigDecimal waist,
                @PositiveOrZero BigDecimal hip,
                @PositiveOrZero BigDecimal neck,
                @PositiveOrZero BigDecimal abdomen,
                @PositiveOrZero BigDecimal chest,

                // --- Membros ---
                @PositiveOrZero BigDecimal rightArm,
                @PositiveOrZero BigDecimal leftArm,
                @PositiveOrZero BigDecimal rightThigh,
                @PositiveOrZero BigDecimal leftThigh,
                @PositiveOrZero BigDecimal rightCalf,
                @PositiveOrZero BigDecimal leftCalf,

                // --- Dobras cutâneas ---
                @PositiveOrZero BigDecimal tricepsFold,
                @PositiveOrZero BigDecimal subscapularFold,
                @PositiveOrZero BigDecimal suprailiacFold,
                @PositiveOrZero BigDecimal abdominalFold,

                String observations

) {
}