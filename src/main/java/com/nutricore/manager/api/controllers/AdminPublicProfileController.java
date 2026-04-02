package com.nutricore.manager.api.controllers;

import com.nutricore.manager.api.dto.PublicProfileAdminRequestDTO;
import com.nutricore.manager.api.dto.PublicProfileAdminResponseDTO;
import com.nutricore.manager.domain.services.PublicProfileAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/public-profile")
@Tag(name = "Admin Public Profile", description = "Administração privada do perfil público do site")
public class AdminPublicProfileController {

    private final PublicProfileAdminService publicProfileAdminService;

    @GetMapping
    @Operation(summary = "Retorna a configuração pública do site")
    public ResponseEntity<PublicProfileAdminResponseDTO> getProfile() {
        return ResponseEntity.ok(publicProfileAdminService.getProfile());
    }

    @PutMapping
    @Operation(summary = "Atualiza a configuração pública do site")
    public ResponseEntity<PublicProfileAdminResponseDTO> updateProfile(
            @Valid @RequestBody PublicProfileAdminRequestDTO request
    ) {
        return ResponseEntity.ok(publicProfileAdminService.updateProfile(request));
    }
}
