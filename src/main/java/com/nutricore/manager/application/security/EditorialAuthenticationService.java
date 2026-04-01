package com.nutricore.manager.application.security;

import com.nutricore.manager.domain.entities.UserAccount;
import com.nutricore.manager.domain.exceptions.InvalidCredentialsException;
import com.nutricore.manager.domain.exceptions.ResourceNotFoundException;
import com.nutricore.manager.infrastructure.db.repositories.UserAccountRepository;
import com.nutricore.manager.infrastructure.security.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EditorialAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserAccountRepository userAccountRepository;
    private final JwtTokenService jwtTokenService;

    @Transactional(readOnly = true)
    public AuthenticationResult authenticate(String email, String password) {
        String normalizedEmail = normalizeEmail(email);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(normalizedEmail, password)
            );
        } catch (DisabledException ex) {
            throw new InvalidCredentialsException("Sua conta editorial esta desativada.");
        } catch (BadCredentialsException ex) {
            throw new InvalidCredentialsException("Email ou senha invalidos.");
        }

        UserAccount userAccount = userAccountRepository.findByEmailIgnoreCase(normalizedEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario editorial nao encontrado."));

        return buildAuthenticationResult(userAccount);
    }

    @Transactional(readOnly = true)
    public AuthenticatedUserSnapshot getAuthenticatedUser(String email) {
        UserAccount userAccount = userAccountRepository.findByEmailIgnoreCase(normalizeEmail(email))
                .orElseThrow(() -> new ResourceNotFoundException("Usuario editorial nao encontrado."));

        return toSnapshot(userAccount);
    }

    private AuthenticationResult buildAuthenticationResult(UserAccount userAccount) {
        var expiresAt = jwtTokenService.calculateExpiry();
        String accessToken = jwtTokenService.generateAccessToken(userAccount, expiresAt);
        return new AuthenticationResult(accessToken, expiresAt, toSnapshot(userAccount));
    }

    private AuthenticatedUserSnapshot toSnapshot(UserAccount userAccount) {
        return new AuthenticatedUserSnapshot(
                userAccount.getId(),
                userAccount.getFullName(),
                userAccount.getEmail(),
                userAccount.getRole()
        );
    }

    private String normalizeEmail(String email) {
        return email == null ? "" : email.trim().toLowerCase();
    }
}
