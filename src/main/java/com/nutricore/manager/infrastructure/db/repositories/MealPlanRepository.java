package com.nutricore.manager.infrastructure.db.repositories;

import com.nutricore.manager.domain.entities.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MealPlanRepository extends JpaRepository<MealPlan, UUID> {
}
