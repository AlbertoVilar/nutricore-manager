package com.nutricore.manager.domain.entities;

import com.nutricore.manager.domain.enums.goal.ActivityLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_anthropometric_assessments")
@EntityListeners(AuditingEntityListener.class)
@Schema(description = "Entidade que representa a avaliação antropométrica e composição corporal")
public class AnthropometricAssessment {

    private static final int SCALE = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(nullable = false)
    private LocalDate assessmentDate;

    // --- Antropometria Básica ---
    @Column(precision = 10, scale = 2)
    private BigDecimal weight; // kg

    @Column(precision = 10, scale = 2)
    private BigDecimal height; // metros

    @Column(precision = 10, scale = 2)
    private BigDecimal bmi; // IMC

    // --- Composição Corporal ---
    @Column(precision = 10, scale = 2)
    private BigDecimal bodyFatPercentage;

    @Column(precision = 10, scale = 2)
    private BigDecimal fatMassKg;

    @Column(precision = 10, scale = 2)
    private BigDecimal leanMassKg;

    @Column(precision = 10, scale = 2)
    private BigDecimal leanMassPercentage;

    @Column(precision = 10, scale = 2)
    private BigDecimal muscleMassKg;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalBodyWater;

    @Column(precision = 10, scale = 2)
    private BigDecimal visceralFat;

    // =======================
    // ENERGIA (METABOLISMO)
    // =======================

    @Column(precision = 10, scale = 2)
    private BigDecimal basalMetabolicRate; // BMR

    @Column(precision = 10, scale = 2)
    private BigDecimal totalEnergyExpenditure; // TDEE

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private ActivityLevel activityLevel;

    // --- Perímetros ---
    @Column(precision = 10, scale = 2)
    private BigDecimal waist;

    @Column(precision = 10, scale = 2)
    private BigDecimal hip;

    @Column(precision = 10, scale = 2)
    private BigDecimal neck;

    @Column(precision = 10, scale = 2)
    private BigDecimal abdomen;

    @Column(precision = 10, scale = 2)
    private BigDecimal chest;

    @Column(precision = 10, scale = 2)
    private BigDecimal waistHipRatio;

    // --- Membros ---
    @Column(precision = 10, scale = 2)
    private BigDecimal rightArm;

    @Column(precision = 10, scale = 2)
    private BigDecimal leftArm;

    @Column(precision = 10, scale = 2)
    private BigDecimal rightThigh;

    @Column(precision = 10, scale = 2)
    private BigDecimal leftThigh;

    @Column(precision = 10, scale = 2)
    private BigDecimal rightCalf;

    @Column(precision = 10, scale = 2)
    private BigDecimal leftCalf;

    // --- Dobras Cutâneas ---
    @Column(precision = 10, scale = 2)
    private BigDecimal tricepsFold;

    @Column(precision = 10, scale = 2)
    private BigDecimal subscapularFold;

    @Column(precision = 10, scale = 2)
    private BigDecimal suprailiacFold;

    @Column(precision = 10, scale = 2)
    private BigDecimal abdominalFold;

    @Column(columnDefinition = "TEXT")
    private String observations;

    // --- Auditoria ---
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // =======================
    // CÁLCULOS DE DOMÍNIO
    // =======================

    @PrePersist
    @PreUpdate
    protected void performCalculations() {
        calculateBmi();
        calculateWaistHipRatio();
        calculateBodyComposition();
    }

    private void calculateBmi() {
        if (weight != null && height != null && height.compareTo(BigDecimal.ZERO) > 0) {

            BigDecimal heightSquared = height.multiply(height);

            this.bmi = weight
                    .divide(heightSquared, SCALE, RoundingMode.HALF_UP);
        }
    }

    private void calculateWaistHipRatio() {
        if (waist != null && hip != null && hip.compareTo(BigDecimal.ZERO) > 0) {

            this.waistHipRatio = waist
                    .divide(hip, SCALE, RoundingMode.HALF_UP);
        }
    }

    private void calculateBodyComposition() {
        if (weight != null && bodyFatPercentage != null) {

            BigDecimal hundred = BigDecimal.valueOf(100);

            // Massa Gorda = peso * (% gordura / 100)
            this.fatMassKg = weight
                    .multiply(bodyFatPercentage)
                    .divide(hundred, SCALE, RoundingMode.HALF_UP);

            // Massa Magra = peso - massa gorda
            this.leanMassKg = weight
                    .subtract(fatMassKg)
                    .setScale(SCALE, RoundingMode.HALF_UP);

            // % Massa Magra
            this.leanMassPercentage = hundred
                    .subtract(bodyFatPercentage)
                    .setScale(SCALE, RoundingMode.HALF_UP);
        }
    }
}
