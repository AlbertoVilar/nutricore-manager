package com.nutricore.manager.api.dto;


import com.nutricore.manager.domain.enums.anamneses.BowelFunction;
import com.nutricore.manager.domain.enums.anamneses.SleepQuality;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record ClinicalAnamnesisRequestDTO(

        @Schema(description = "ID do paciente associado", example = "1")
        @NotNull(message = "O ID do paciente é obrigatório")
        Long patientId,

        @Schema(description = "Histórico clínico (doenças, cirurgias, etc)", example = "Gastrite crônica diagnosticada em 2022")
        String clinicalHistory,

        @Schema(description = "Histórico de doenças na família", example = "Pai com hipertensão e diabetes")
        String familyHistory,

        @Schema(description = "Medicamentos em uso", example = "Omeprazol 20mg em jejum")
        String medicationUse,

        @Schema(description = "Qualidade do sono", example = "GOOD")
        @NotNull(message = "A qualidade do sono é obrigatória")
        SleepQuality sleepQuality,

        @Schema(description = "Funcionamento do intestino", example = "REGULAR")
        @NotNull(message = "A função intestinal é obrigatória")
        BowelFunction bowelFunction,

        @Schema(description = "Meta de consumo de água (litros)", example = "2.5")
        @Positive(message = "A meta de água deve ser um valor positivo")
        Double waterIntakeGoal,

        @Schema(description = "Data da anamnese", example = "2023-10-27")
        @NotNull(message = "A data é obrigatória")
        @PastOrPresent(message = "A data não pode ser futura")
        LocalDate date
) {
}