package com.nutricore.manager.api.controllers;

import com.nutricore.manager.api.dto.AdminUserCreateRequestDTO;
import com.nutricore.manager.api.dto.AdminUserPasswordRequestDTO;
import com.nutricore.manager.api.dto.AdminUserResponseDTO;
import com.nutricore.manager.api.dto.AdminUserUpdateRequestDTO;
import com.nutricore.manager.application.security.UserAccountAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/v1/admin/users")
@Tag(name = "Admin Users", description = "Gestão de usuários editoriais da área privada")
public class AdminUserController {

    private final UserAccountAdminService userAccountAdminService;

    @GetMapping
    @Operation(summary = "Lista os usuários editoriais")
    public ResponseEntity<List<AdminUserResponseDTO>> findAll() {
        return ResponseEntity.ok(userAccountAdminService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuário editorial por ID")
    public ResponseEntity<AdminUserResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userAccountAdminService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Cria um novo usuário editorial")
    public ResponseEntity<AdminUserResponseDTO> create(@Valid @RequestBody AdminUserCreateRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userAccountAdminService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um usuário editorial")
    public ResponseEntity<AdminUserResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody AdminUserUpdateRequestDTO request,
            Authentication authentication
    ) {
        return ResponseEntity.ok(userAccountAdminService.update(id, request, authentication.getName()));
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Ativa um usuário editorial")
    public ResponseEntity<AdminUserResponseDTO> activate(@PathVariable Long id) {
        return ResponseEntity.ok(userAccountAdminService.activate(id));
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Desativa um usuário editorial")
    public ResponseEntity<AdminUserResponseDTO> deactivate(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return ResponseEntity.ok(userAccountAdminService.deactivate(id, authentication.getName()));
    }

    @PatchMapping("/{id}/password")
    @Operation(summary = "Redefine a senha de um usuário editorial")
    public ResponseEntity<Void> resetPassword(
            @PathVariable Long id,
            @Valid @RequestBody AdminUserPasswordRequestDTO request
    ) {
        userAccountAdminService.resetPassword(id, request);
        return ResponseEntity.noContent().build();
    }
}
