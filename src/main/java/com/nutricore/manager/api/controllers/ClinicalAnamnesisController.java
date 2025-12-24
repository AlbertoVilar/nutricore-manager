package com.nutricore.manager.api.controllers;

import com.nutricore.manager.api.dto.ClinicalAnamnesisRequest;
import com.nutricore.manager.api.dto.ClinicalAnamnesisResponse;
import com.nutricore.manager.domain.services.ClinicalAnamnesisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/anamnesis")
@RequiredArgsConstructor
@Tag(name = "Clinical Anamnesis", description = "Endpoints para gestão de anamneses clínicas")
public class ClinicalAnamnesisController {

    private final ClinicalAnamnesisService service;

    @PostMapping
    @Operation(summary = "Registrar nova anamnese", description = "Cria um registro de anamnese vinculado a um paciente")
    public ResponseEntity<ClinicalAnamnesisResponse> create(
                                @RequestBody @Valid ClinicalAnamnesisRequest request) {

        var response = service.createAnamnesis(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
