package com.nutricore.manager.api.dto;

import com.nutricore.manager.domain.enums.Gender;
import com.nutricore.manager.domain.enums.LifeStyle;
import com.nutricore.manager.domain.enums.PatientStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PatientResponse(

        Long id,
        String name,
        String email,
        String phone,
        LocalDate birthDate, // Alterado para LocalDate
        Gender gender,       // Alterado para o Enum
        String occupation,
        LifeStyle lifeStyle, // Alterado para o Enum
        PatientStatus status,// Alterado para o Enum
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}
