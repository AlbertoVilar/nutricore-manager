package com.nutricore.manager.infrastructure.db.repositories;

import com.nutricore.manager.domain.entities.ClinicalAnamnesis;
import com.nutricore.manager.domain.enums.anamneses.BowelFunction;
import com.nutricore.manager.domain.enums.anamneses.SleepQuality;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ClinicalAnamnesisRepository extends JpaRepository<ClinicalAnamnesis, Long> {

    // Busca todas as anamneses de um paciente específico por ID
    //Page<ClinicalAnamnesis> findByPatientIdOrderByDateDesc(Long patientId, Pageable pageable);

    @Query("SELECT a FROM ClinicalAnamnesis a WHERE a.patient.id = :patientId " +
            "AND (:startDate IS NULL OR a.date >= :startDate) " +
            "AND (:endDate IS NULL OR a.date <= :endDate) " +
            "AND (:sleep IS NULL OR a.sleepQuality = :sleep) " +
            "AND (:bowel IS NULL OR a.bowelFunction = :bowel)")
    Page<ClinicalAnamnesis> findByPatientIdWithFilters(
            @Param("patientId") Long patientId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("sleep") SleepQuality sleep,
            @Param("bowel") BowelFunction bowel,
            Pageable pageable
    );
}
