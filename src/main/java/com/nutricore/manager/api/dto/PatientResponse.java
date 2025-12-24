package com.nutricore.manager.api.dto;

import com.nutricore.manager.domain.enums.patient.Gender;
import com.nutricore.manager.domain.enums.patient.LifeStyle;
import com.nutricore.manager.domain.enums.patient.PatientStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Objeto de resposta com os dados detalhados do paciente")
public record PatientResponse(

        @Schema(description = "Identificador único do paciente", example = "1")
        Long id,

        @Schema(description = "Nome completo do paciente", example = "Alberto Vilar")
        String name,

        @Schema(description = "Endereço de e-mail principal", example = "alberto.vilar@email.com")
        String email,

        @Schema(description = "Telefone celular formatado", example = "11988887777")
        String phone,

        @Schema(description = "Data de nascimento no padrão ISO", example = "1990-05-15")
        LocalDate birthDate,

        @Schema(description = "Gênero do paciente", example = "MALE")
        Gender gender,

        @Schema(description = "Profissão ou ocupação principal", example = "Engenheiro de Software")
        String occupation,

        @Schema(description = "Nível de atividade física e estilo de vida", example = "ACTIVE")
        LifeStyle lifeStyle,

        @Schema(description = "Status atual do cadastro no sistema", example = "ACTIVE")
        PatientStatus status,

        @Schema(description = "Data e hora de criação do registro", example = "2023-10-27T10:00:00")
        LocalDateTime createdAt,

        @Schema(description = "Data e hora da última atualização do registro", example = "2023-10-28T14:30:00")
        LocalDateTime updatedAt

) {
}