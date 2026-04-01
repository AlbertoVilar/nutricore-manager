package com.nutricore.manager.infrastructure.db.repositories;

import com.nutricore.manager.domain.entities.Recipe;
import com.nutricore.manager.domain.enums.editorial.EditorialStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findAllByOrderByUpdatedAtDesc();

    List<Recipe> findAllByStatusOrderByUpdatedAtDesc(EditorialStatus status);

    List<Recipe> findAllByStatusOrderByPublishedAtDesc(EditorialStatus status);

    Optional<Recipe> findBySlugAndStatus(String slug, EditorialStatus status);

    boolean existsBySlug(String slug);

    boolean existsBySlugAndIdNot(String slug, Long id);
}
