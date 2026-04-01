package com.nutricore.manager.infrastructure.db.repositories;

import com.nutricore.manager.domain.entities.PublicPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublicPostRepository extends JpaRepository<PublicPost, Long> {

    List<PublicPost> findAllByOrderByPublishedAtDesc();
}
