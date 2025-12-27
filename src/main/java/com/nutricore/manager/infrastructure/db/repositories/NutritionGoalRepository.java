package com.nutricore.manager.infrastructure.db.repositories;

import com.nutricore.manager.domain.entities.NutritionGoal;
import com.nutricore.manager.domain.enums.goal.GoalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NutritionGoalRepository extends JpaRepository<NutritionGoal, Long> {

    Page<NutritionGoal> findByPatientId(Long patientId, Pageable pageable);

    Optional<NutritionGoal> findByPatientIdAndStatus(Long patientId, GoalStatus status);
}
