package com.nutricore.manager.application.security;

import com.nutricore.manager.domain.enums.security.UserRole;

public record AuthenticatedUserSnapshot(
        Long id,
        String fullName,
        String email,
        UserRole role
) {
}
