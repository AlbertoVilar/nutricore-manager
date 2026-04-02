package com.nutricore.manager.infrastructure.db.repositories;

import com.nutricore.manager.domain.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    Optional<UserAccount> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);

    boolean existsByEmailIgnoreCase(String email);

    List<UserAccount> findAllByOrderByFullNameAsc();
}
