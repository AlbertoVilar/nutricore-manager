package com.nutricore.manager.application.security;

import com.nutricore.manager.domain.entities.UserAccount;
import com.nutricore.manager.domain.enums.security.UserRole;
import com.nutricore.manager.infrastructure.db.repositories.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAccountProvisioningService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void provisionBootstrapUser(
            String fullName,
            String email,
            String rawPassword,
            UserRole role
    ) {
        UserAccount userAccount = userAccountRepository.findByEmailIgnoreCase(email)
                .orElseGet(UserAccount::new);

        userAccount.setFullName(fullName);
        userAccount.setEmail(email.trim().toLowerCase());
        userAccount.setPasswordHash(passwordEncoder.encode(rawPassword));
        userAccount.setRole(role);
        userAccount.setActive(true);

        userAccountRepository.save(userAccount);
    }
}
