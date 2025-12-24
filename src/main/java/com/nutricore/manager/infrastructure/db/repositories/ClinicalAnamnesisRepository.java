package com.nutricore.manager.infrastructure.db.repositories;

import com.nutricore.manager.domain.entities.ClinicalAnamnesis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicalAnamnesisRepository extends JpaRepository<ClinicalAnamnesis, Long> {

    // Busca todas as anamneses de um paciente específico por ID
    List<ClinicalAnamnesis> findByPatientIdOrderByDateDesc(Long patientId);
}
