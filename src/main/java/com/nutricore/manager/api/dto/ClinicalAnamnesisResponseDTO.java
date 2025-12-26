package com.nutricore.manager.api.dto;

import com.nutricore.manager.domain.enums.anamneses.BowelFunction;
import com.nutricore.manager.domain.enums.anamneses.SleepQuality;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ClinicalAnamnesisResponseDTO(
        @Schema(example = "1")
        Long id,

        @Schema(example = "Gastrite crônica...")
        String clinicalHistory,

        @Schema(example = "Pai com hipertensão...")
        String familyHistory,

        String medicationUse,
        SleepQuality sleepQuality,
        BowelFunction bowelFunction,
        Double waterIntakeGoal,
        LocalDate date,
        LocalDateTime createdAt
) {
}