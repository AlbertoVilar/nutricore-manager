package com.nutricore.manager.api.dto;

import com.nutricore.manager.domain.enums.security.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AdminUserCreateRequestDTO(
        @NotBlank @Size(max = 120) String fullName,
        @NotBlank @Email @Size(max = 160) String email,
        @NotBlank @Size(min = 6, max = 120) String password,
        @NotNull UserRole role,
        @NotNull Boolean active
) {
}
