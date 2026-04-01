package com.nutricore.manager.infrastructure.db.repositories;

import com.nutricore.manager.domain.entities.Article;
import com.nutricore.manager.domain.enums.editorial.EditorialStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAllByOrderByUpdatedAtDesc();

    List<Article> findAllByStatusOrderByUpdatedAtDesc(EditorialStatus status);

    List<Article> findAllByStatusOrderByPublishedAtDesc(EditorialStatus status);

    Optional<Article> findBySlugAndStatus(String slug, EditorialStatus status);

    boolean existsBySlug(String slug);

    boolean existsBySlugAndIdNot(String slug, Long id);
}
