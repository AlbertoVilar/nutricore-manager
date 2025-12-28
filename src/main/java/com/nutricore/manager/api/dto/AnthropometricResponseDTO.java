package com.nutricore.manager.api.dto;

import com.nutricore.manager.domain.enums.goal.ActivityLevel;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record AnthropometricResponseDTO(

                Long id,
                Long patientId,
                LocalDate assessmentDate,

                // --- Antropometria Básica e Cálculos ---
                BigDecimal weight,
                BigDecimal height,
                BigDecimal bmi,
                String bmiClassification,

                // --- Composição Corporal ---
                BigDecimal bodyFatPercentage,
                BigDecimal fatMassKg,
                BigDecimal leanMassKg,
                BigDecimal leanMassPercentage,
                BigDecimal muscleMassKg,
                BigDecimal totalBodyWater,
                BigDecimal visceralFat,
                BigDecimal basalMetabolicRate,
                BigDecimal totalEnergyExpenditure,
                ActivityLevel activityLevel,

                // --- Perímetros e RCQ ---
                BigDecimal waist,
                BigDecimal hip,
                BigDecimal neck,
                BigDecimal abdomen,
                BigDecimal chest,
                BigDecimal waistHipRatio,
                String waistHipRisk,

                // --- Membros ---
                BigDecimal rightArm,
                BigDecimal leftArm,
                BigDecimal rightThigh,
                BigDecimal leftThigh,
                BigDecimal rightCalf,
                BigDecimal leftCalf,

                // --- Dobras Cutâneas ---
                BigDecimal tricepsFold,
                BigDecimal subscapularFold,
                BigDecimal suprailiacFold,
                BigDecimal abdominalFold,

                String observations,

                // --- Auditoria ---
                LocalDateTime createdAt,
                LocalDateTime updatedAt) {
}
