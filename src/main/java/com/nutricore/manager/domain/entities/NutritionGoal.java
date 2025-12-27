package com.nutricore.manager.domain.entities;

import com.nutricore.manager.domain.enums.goal.GoalStatus;
import com.nutricore.manager.domain.enums.goal.GoalType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_nutrition_goals")
@EntityListeners(AuditingEntityListener.class)
@Schema(description = "Entidade que representa um objetivo nutricional do paciente")
public class NutritionGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ======================
    // RELACIONAMENTO
    // ======================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // ======================
    // OBJETIVO
    // ======================

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private GoalType goalType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private GoalStatus status;

    // ======================
    // METAS (valores-alvo)
    // ======================

    @Column(precision = 10, scale = 2)
    private BigDecimal targetWeight;

    @Column(precision = 10, scale = 2)
    private BigDecimal targetBodyFatPercentage;

    @Column(precision = 10, scale = 2)
    private BigDecimal targetLeanMass; // Massa magra

    // ======================
    // PERÍODO
    // ======================

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate expectedEndDate;

    // ======================
    // OBSERVAÇÕES
    // ======================

    @Column(columnDefinition = "TEXT")
    private String notes;

    // ======================
    // AUDITORIA
    // ======================

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // ======================
    // COMPORTAMENTO DE DOMÍNIO
    // ======================

    public void activate() {
        this.status = GoalStatus.ACTIVE;
    }

    public void complete() {
        this.status = GoalStatus.COMPLETED;
    }

    public void cancel() {
        this.status = GoalStatus.CANCELLED;
    }

    public boolean isActive() {
        return this.status == GoalStatus.ACTIVE;
    }

    /**
     * Verifica se o objetivo nutricional está em atraso.
     *
     * Um objetivo é considerado em atraso quando possui uma data prevista de conclusão
     * definida e a data atual já ultrapassou essa previsão.
     *
     * @return true se o objetivo estiver atrasado; false caso contrário
     */
    public boolean isOverdue() {
        return expectedEndDate != null && LocalDate.now().isAfter(expectedEndDate);
    }


    /**
     * Retorna a quantidade de dias de atraso do objetivo nutricional.
     *
     * Caso o objetivo não possua data prevista ou ainda não esteja atrasado,
     * o valor retornado será 0.
     *
     * @return número de dias de atraso
     */
    public long getOverdueDays() {
        if (expectedEndDate == null) {
            return 0;
        }

        long days = ChronoUnit.DAYS.between(expectedEndDate, LocalDate.now());
        return Math.max(days, 0);
    }

}
