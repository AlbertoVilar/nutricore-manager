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

        @Operation(summary = "Atualiza uma avaliação", description = "Atualiza dados brutos e força o recálculo de índices. Campos nulos no request serão ignorados (mantém o valor atual).")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Avaliação atualizada com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Erro de validação ou troca de paciente indevida"),
                        @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
        })
        @PutMapping("/assessments/{id}")
        public ResponseEntity<AnthropometricResponseDTO> update(
                        @PathVariable Long id,
                        @RequestBody @Valid AnthropometricRequestDTO request) {
                return ResponseEntity.ok(service.update(id, request));
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

        @Operation(summary = "Remove uma avaliação", description = "Exclui permanentemente um registro de avaliação antropométrica.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Avaliação removida com sucesso"),
                        @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
        })
        @DeleteMapping("/assessments/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
                service.delete(id);
                return ResponseEntity.noContent().build();
        }
}