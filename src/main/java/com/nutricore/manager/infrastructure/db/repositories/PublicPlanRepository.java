package com.nutricore.manager.infrastructure.db.repositories;

import com.nutricore.manager.domain.entities.PublicPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicPlanRepository extends JpaRepository<PublicPlan, Long> {

    List<PublicPlan> findAllByOrderByDisplayOrderAsc();
}
