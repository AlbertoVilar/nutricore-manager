package com.nutricore.manager.api.controllers;

import com.nutricore.manager.api.dto.AuthLoginRequestDTO;
import com.nutricore.manager.api.dto.AuthSessionResponseDTO;
import com.nutricore.manager.api.dto.AuthenticatedUserResponseDTO;
import com.nutricore.manager.application.security.AuthenticatedUserSnapshot;
import com.nutricore.manager.application.security.AuthenticationResult;
import com.nutricore.manager.application.security.EditorialAuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
@Tag(name = "Autenticação", description = "Autenticação e sessão da área privada")
public class AuthController {

    private final EditorialAuthenticationService editorialAuthenticationService;

    @PostMapping("/login")
    @Operation(summary = "Autentica um usuário editorial")
    public ResponseEntity<AuthSessionResponseDTO> login(@Valid @RequestBody AuthLoginRequestDTO request) {
        AuthenticationResult result = editorialAuthenticationService.authenticate(request.email(), request.password());
        return ResponseEntity.ok(toSessionResponse(result));
    }

    @GetMapping("/me")
    @Operation(summary = "Retorna o usuário autenticado na sessão atual")
    public ResponseEntity<AuthenticatedUserResponseDTO> me(Authentication authentication) {
        AuthenticatedUserSnapshot snapshot = editorialAuthenticationService.getAuthenticatedUser(authentication.getName());
        return ResponseEntity.ok(toUserResponse(snapshot));
    }

    private AuthSessionResponseDTO toSessionResponse(AuthenticationResult result) {
        return new AuthSessionResponseDTO(
                result.accessToken(),
                "Bearer",
                result.expiresAt().toString(),
                toUserResponse(result.user())
        );
    }

    private AuthenticatedUserResponseDTO toUserResponse(AuthenticatedUserSnapshot snapshot) {
        return new AuthenticatedUserResponseDTO(
                snapshot.id(),
                snapshot.fullName(),
                snapshot.email(),
                snapshot.role().name()
        );
    }
}
