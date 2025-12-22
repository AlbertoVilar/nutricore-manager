package com.nutricore.manager.api.dto;

import com.nutricore.manager.domain.enums.Gender;
import com.nutricore.manager.domain.enums.LifeStyle;
import com.nutricore.manager.domain.enums.PatientStatus;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record PatientRequest(

        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
        String name,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "O e-mail deve ser válido")
        String email,

        @NotBlank(message = "O telefone é obrigatório")
        @Pattern(regexp = "^\\d{11}$", message = "O telefone deve conter 11 dígitos numéricos (Ex: 11988887777)")
        String phone,

        @NotNull(message = "A data de nascimento é obrigatória")
        @Past(message = "A data de nascimento deve ser uma data passada")
        LocalDate birthDate,

        @NotNull(message = "O gênero é obrigatório")
        Gender gender,

        String occupation,

        @NotNull(message = "O estilo de vida é obrigatório")
        LifeStyle lifeStyle,

        @NotNull(message = "O status do paciente é obrigatório")
        PatientStatus status
) {
}