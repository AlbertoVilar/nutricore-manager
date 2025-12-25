package com.nutricore.manager.infrastructure.db.repositories;

import com.nutricore.manager.domain.entities.ClinicalAnamnesis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicalAnamnesisRepository extends JpaRepository<ClinicalAnamnesis, Long> {

    // Busca todas as anamneses de um paciente específico por ID
    Page<ClinicalAnamnesis> findByPatientIdOrderByDateDesc(Long patientId, Pageable pageable);
}
