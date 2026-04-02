package com.nutricore.manager.application.security;

import com.nutricore.manager.api.dto.AdminUserCreateRequestDTO;
import com.nutricore.manager.api.dto.AdminUserPasswordRequestDTO;
import com.nutricore.manager.api.dto.AdminUserResponseDTO;
import com.nutricore.manager.api.dto.AdminUserUpdateRequestDTO;
import com.nutricore.manager.domain.entities.UserAccount;
import com.nutricore.manager.domain.exceptions.BusinessException;
import com.nutricore.manager.domain.exceptions.ResourceNotFoundException;
import com.nutricore.manager.infrastructure.db.repositories.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAccountAdminService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<AdminUserResponseDTO> findAll() {
        return userAccountRepository.findAllByOrderByFullNameAsc().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public AdminUserResponseDTO findById(Long id) {
        return toResponse(getUser(id));
    }

    @Transactional
    public AdminUserResponseDTO create(AdminUserCreateRequestDTO request) {
        String normalizedEmail = normalizeEmail(request.email());
        if (userAccountRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new BusinessException("Já existe um usuário editorial com este e-mail.");
        }

        UserAccount entity = UserAccount.builder()
                .fullName(request.fullName().trim())
                .email(normalizedEmail)
                .passwordHash(passwordEncoder.encode(request.password()))
                .role(request.role())
                .active(request.active())
                .build();

        return toResponse(userAccountRepository.save(entity));
    }

    @Transactional
    public AdminUserResponseDTO update(Long id, AdminUserUpdateRequestDTO request, String authenticatedEmail) {
        UserAccount entity = getUser(id);
        String normalizedEmail = normalizeEmail(request.email());

        if (userAccountRepository.existsByEmailIgnoreCaseAndIdNot(normalizedEmail, id)) {
            throw new BusinessException("Já existe um usuário editorial com este e-mail.");
        }

        if (isSelf(entity, authenticatedEmail) && entity.getRole() != request.role()) {
            throw new BusinessException("Você não pode alterar o próprio papel pela área administrativa.");
        }

        if (isSelf(entity, authenticatedEmail) && !request.active()) {
            throw new BusinessException("Você não pode desativar a própria conta.");
        }

        entity.setFullName(request.fullName().trim());
        entity.setEmail(normalizedEmail);
        entity.setRole(request.role());
        entity.setActive(request.active());

        return toResponse(userAccountRepository.save(entity));
    }

    @Transactional
    public AdminUserResponseDTO activate(Long id) {
        UserAccount entity = getUser(id);
        entity.setActive(true);
        return toResponse(userAccountRepository.save(entity));
    }

    @Transactional
    public AdminUserResponseDTO deactivate(Long id, String authenticatedEmail) {
        UserAccount entity = getUser(id);

        if (isSelf(entity, authenticatedEmail)) {
            throw new BusinessException("Você não pode desativar a própria conta.");
        }

        entity.setActive(false);
        return toResponse(userAccountRepository.save(entity));
    }

    @Transactional
    public void resetPassword(Long id, AdminUserPasswordRequestDTO request) {
        UserAccount entity = getUser(id);
        entity.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        userAccountRepository.save(entity);
    }

    private UserAccount getUser(Long id) {
        return userAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário editorial não encontrado com ID: " + id));
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase();
    }

    private boolean isSelf(UserAccount entity, String authenticatedEmail) {
        return entity.getEmail().equalsIgnoreCase(authenticatedEmail);
    }

    private AdminUserResponseDTO toResponse(UserAccount entity) {
        return new AdminUserResponseDTO(
                entity.getId(),
                entity.getFullName(),
                entity.getEmail(),
                entity.getRole().name(),
                entity.getActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
