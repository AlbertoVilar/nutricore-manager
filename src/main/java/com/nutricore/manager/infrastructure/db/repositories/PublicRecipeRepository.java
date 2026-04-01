package com.nutricore.manager.infrastructure.db.repositories;

import com.nutricore.manager.domain.entities.PublicRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicRecipeRepository extends JpaRepository<PublicRecipe, Long> {

    List<PublicRecipe> findAllByOrderByDisplayOrderAsc();
}
