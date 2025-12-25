package com.nutricore.manager.api.controllers;

import com.nutricore.manager.api.dto.ClinicalAnamnesisRequest;
import com.nutricore.manager.api.dto.ClinicalAnamnesisResponse;
import com.nutricore.manager.domain.exceptions.error.StandardError;
import com.nutricore.manager.domain.services.ClinicalAnamnesisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/anamnesis")
@RequiredArgsConstructor
@Tag(name = "Clinical Anamnesis", description = "Endpoints para gestão de anamneses clínicas")
public class ClinicalAnamnesisController {

    private final ClinicalAnamnesisService service;

    @PostMapping
    @Operation(summary = "Registrar nova anamnese", description = "Cria um registro de anamnese vinculado a um paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Anamnese criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos.",
                    content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado.",
                    content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    public ResponseEntity<ClinicalAnamnesisResponse> create(
            @RequestBody @Valid ClinicalAnamnesisRequest request) {

        var response = service.createAnamnesis(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /ClinicalAnamnesis/{id}
    @GetMapping("/patient/{patientId}")
    @Operation(summary = "Listar histórico de anamneses do paciente",
            description = "Retorna uma página de registros de anamnese para um paciente específico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico recuperado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado.",
                    content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    public ResponseEntity<Page<ClinicalAnamnesisResponse>> getHistory(
            @PathVariable Long patientId,
            @ParameterObject // Fundamental para o Swagger
            @PageableDefault(size = 12) Pageable pageable) {
        Page<ClinicalAnamnesisResponse> anamnesisResponses = service.getHistoryByPatientId(patientId, pageable);
        return ResponseEntity.ok(anamnesisResponses);
    }
}
