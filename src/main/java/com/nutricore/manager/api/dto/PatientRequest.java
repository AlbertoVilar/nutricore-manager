package com.nutricore.manager.api.dto;

import com.nutricore.manager.domain.enums.Gender;
import com.nutricore.manager.domain.enums.LifeStyle;
import com.nutricore.manager.domain.enums.PatientStatus;

import java.time.LocalDate;

public record PatientRequest(

        String name,
        String email,
        String phone,
        LocalDate birthDate,
        Gender gender,
        String occupation,
        LifeStyle lifeStyle,
        PatientStatus status


) {
}
