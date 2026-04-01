package com.nutricore.manager.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequestDTO(
        @NotBlank(message = "Informe o email.")
        @Email(message = "Informe um email valido.")
        String email,

        @NotBlank(message = "Informe a senha.")
        String password
) {
}
