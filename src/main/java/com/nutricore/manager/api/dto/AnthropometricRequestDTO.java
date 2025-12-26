package com.nutricore.manager.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public record AnthropometricRequestDTO(

        @NotNull(message = "O ID do paciente é obrigatório")
        Long patientId,

        @NotNull(message = "A data da avaliação é obrigatória")
        @PastOrPresent(message = "A data não pode ser no futuro")
        LocalDate assessmentDate,

        // --- Antropometria Básica (Aqui o zero não faz sentido) ---
        @NotNull(message = "O peso é obrigatório")
        @Positive(message = "O peso deve ser maior que zero")
        Double weight,

        @NotNull(message = "A altura é obrigatória")
        @Positive(message = "A altura deve ser maior que zero")
        Double height,

        // --- Composição corporal (Podem ser zero ou nulos) ---
        @PositiveOrZero Double bodyFatPercentage,
        @PositiveOrZero Double muscleMassKg,
        @PositiveOrZero Double totalBodyWater,
        @PositiveOrZero Double visceralFat,
        @PositiveOrZero Double basalMetabolicRate,

        // --- Perímetros (Podem ser zero se não medidos) ---
        @PositiveOrZero Double waist,
        @PositiveOrZero Double hip,
        @PositiveOrZero Double neck,
        @PositiveOrZero Double abdomen,
        @PositiveOrZero Double chest,

        // --- Membros ---
        @PositiveOrZero Double rightArm,
        @PositiveOrZero Double leftArm,
        @PositiveOrZero Double rightThigh,
        @PositiveOrZero Double leftThigh,
        @PositiveOrZero Double rightCalf,
        @PositiveOrZero Double leftCalf,

        // --- Dobras cutâneas ---
        @PositiveOrZero Double tricepsFold,
        @PositiveOrZero Double subscapularFold,
        @PositiveOrZero Double suprailiacFold,
        @PositiveOrZero Double abdominalFold,

        String observations

) {
}