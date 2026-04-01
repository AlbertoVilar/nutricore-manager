package com.nutricore.manager.infrastructure.db.repositories;

import com.nutricore.manager.domain.entities.Post;
import com.nutricore.manager.domain.enums.editorial.EditorialStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByUpdatedAtDesc();

    List<Post> findAllByStatusOrderByUpdatedAtDesc(EditorialStatus status);

    List<Post> findAllByStatusOrderByPublishedAtDesc(EditorialStatus status);

    Optional<Post> findBySlugAndStatus(String slug, EditorialStatus status);

    boolean existsBySlug(String slug);

    boolean existsBySlugAndIdNot(String slug, Long id);
}
