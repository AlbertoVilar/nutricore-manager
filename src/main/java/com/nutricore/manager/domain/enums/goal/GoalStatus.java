package com.nutricore.manager.domain.enums.goal;

/**
 * Representa o ciclo de vida do objetivo.
 */
public enum GoalStatus {

    PLANNED, // Criado, mas ainda não iniciado
    ACTIVE, // Em andamento
    COMPLETED, // Objetivo alcançado
    CANCELLED // Cancelado antes da conclusão
}
