package com.nutricore.manager.api.controllers;

import com.nutricore.manager.api.dto.PublicProfileResponseDTO;
import com.nutricore.manager.domain.services.PublicProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/public/profile")
@Tag(name = "Perfil público", description = "Dados institucionais públicos da nutricionista")
public class PublicProfileController {

    private final PublicProfileService publicProfileService;

    @GetMapping
    @Operation(summary = "Retorna o perfil público principal")
    public ResponseEntity<PublicProfileResponseDTO> getProfile() {
        return ResponseEntity.ok(publicProfileService.getProfile());
    }
}
