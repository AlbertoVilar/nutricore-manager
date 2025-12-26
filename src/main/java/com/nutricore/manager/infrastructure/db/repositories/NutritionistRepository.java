package com.nutricore.manager.infrastructure.db.repositories;

import com.nutricore.manager.domain.entities.Nutritionist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NutritionistRepository extends JpaRepository<Nutritionist, Long> {
}
