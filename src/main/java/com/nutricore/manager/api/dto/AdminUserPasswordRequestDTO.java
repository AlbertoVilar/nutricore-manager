package com.nutricore.manager.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminUserPasswordRequestDTO(
        @NotBlank @Size(min = 6, max = 120) String newPassword
) {
}
