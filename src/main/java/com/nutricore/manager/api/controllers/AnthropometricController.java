package com.nutricore.manager.api.controllers;

import com.nutricore.manager.api.dto.AnthropometricRequestDTO;
import com.nutricore.manager.api.dto.AnthropometricResponseDTO;
import com.nutricore.manager.domain.services.AnthropometricService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/anthropometric")
@RequiredArgsConstructor
@Tag(name = "Anthropometric Assessment", description = "Endpoints para gestão de avaliações físicas e evolução do paciente")
public class AnthropometricController {

        private final AnthropometricService service;

        @Operation(summary = "Cadastra uma nova avaliação", description = "Cria um registro antropométrico vinculado a um paciente. Calcula automaticamente IMC, RCQ, Massa Gorda e Massa Magra.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Avaliação criada com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos ou erro de cálculo"),
                        @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
        })
        @PostMapping("/assessments")
        public ResponseEntity<AnthropometricResponseDTO> create(@RequestBody @Valid AnthropometricRequestDTO request) {
                AnthropometricResponseDTO response = service.create(request);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        @Operation(summary = "Busca histórico por paciente", description = "Retorna uma página de avaliações de um paciente específico, ordenadas da mais recente para a mais antiga.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Listagem recuperada com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
        })
        @GetMapping("/patient/{patientId}")
        public ResponseEntity<Page<AnthropometricResponseDTO>> findAllByPatient(
                        @PathVariable Long patientId,
                        @ParameterObject Pageable pageable) {

                return ResponseEntity.ok(service.findAllByPatientId(patientId, pageable));
        }
}