package com.nutricore.manager.infrastructure.db.repositories;

import com.nutricore.manager.domain.entities.AnthropometricAssessment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnthropometricAssessmentRepository extends JpaRepository<AnthropometricAssessment, Long> {

    // Busca o histórico de avaliações de um paciente específico, ordenado por data
    Page<AnthropometricAssessment> findByPatientIdOrderByAssessmentDateDesc(Long patientId, Pageable pageable);

    // Busca a avaliação mais recente do paciente (útil para comparar com a nova)
    Optional<AnthropometricAssessment> findFirstByPatientIdOrderByAssessmentDateDesc(Long patientId);
}
