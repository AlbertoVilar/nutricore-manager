package com.nutricore.manager.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PatientRequest(

        @Schema(description = "Nome completo do usuário", example = "Alberto Vilar")
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
        String name,

        @Schema(description = "E-mail único do paciente", example = "alberto.vilar@email.com")
        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "O e-mail deve ser válido")
        String email,

        @Schema(description = "Telefone celular com DDD (apenas números)", example = "11988887777")
        @NotBlank(message = "O telefone é obrigatório")
        @Pattern(regexp = "^\\d{11}$", message = "O telefone deve conter 11 dígitos numéricos (Ex: 11988887777)")
        String phone,

        @Schema(description = "Data de nascimento no formato ISO", example = "1990-05-15")
        @NotNull(message = "A data de nascimento é obrigatória")
        @Past(message = "A data de nascimento deve ser uma data passada")
        LocalDate birthDate,

        @Schema(description = "Gênero do paciente", example = "MALE")
        @NotNull(message = "O gênero é obrigatório")
        Gender gender,

        @Schema(description = "Profissão do paciente", example = "Engenheiro de Software")
        String occupation,

        @Schema(description = "Nível de atividade física", example = "ACTIVE")
        @NotNull(message = "O estilo de vida é obrigatório")
        LifeStyle lifeStyle,

        @Schema(description = "Status atual do cadastro", example = "ACTIVE")
        @NotNull(message = "O status do paciente é obrigatório")
        PatientStatus status
) {
}