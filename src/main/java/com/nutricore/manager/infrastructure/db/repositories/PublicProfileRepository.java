package com.nutricore.manager.infrastructure.db.repositories;

import com.nutricore.manager.domain.entities.PublicProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublicProfileRepository extends JpaRepository<PublicProfile, Long> {

    Optional<PublicProfile> findFirstByOrderByIdAsc();
}
