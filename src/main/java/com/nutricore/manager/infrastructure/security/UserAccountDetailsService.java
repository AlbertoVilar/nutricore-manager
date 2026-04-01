package com.nutricore.manager.infrastructure.security;

import com.nutricore.manager.domain.entities.UserAccount;
import com.nutricore.manager.infrastructure.db.repositories.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountDetailsService implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserAccount userAccount = userAccountRepository.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));

        return User.builder()
                .username(userAccount.getEmail())
                .password(userAccount.getPasswordHash())
                .disabled(!Boolean.TRUE.equals(userAccount.getActive()))
                .authorities(new SimpleGrantedAuthority("ROLE_" + userAccount.getRole().name()))
                .build();
    }
}
