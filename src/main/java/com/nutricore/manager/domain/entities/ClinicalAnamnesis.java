package com.nutricore.manager.domain.entities;

import com.nutricore.manager.domain.enums.anamneses.BowelFunction;
import com.nutricore.manager.domain.enums.anamneses.SleepQuality;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_clinical_anamnesis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClinicalAnamnesis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String clinicalHistory;

    @Column(columnDefinition = "TEXT")
    private String familyHistory;

    @Column(columnDefinition = "TEXT")
    private String medicationUse;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private SleepQuality sleepQuality;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private BowelFunction bowelFunction;

    private Double waterIntakeGoal;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY) // Lazy por performance e sem Swagger
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    /**
     * Lógica de Domínio: Verifica se a anamnese foi realizada nos últimos 6 meses.
     * Conforme definido no seu diagrama UML.
     */
    public boolean isRecent() {
        return date != null && date.isAfter(LocalDate.now().minusMonths(6));
    }
}