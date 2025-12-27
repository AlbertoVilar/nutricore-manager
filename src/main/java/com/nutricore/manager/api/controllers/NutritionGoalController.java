package com.nutricore.manager.api.controllers;

import com.nutricore.manager.api.dto.NutritionGoalRequestDTO;
import com.nutricore.manager.api.dto.NutritionGoalResponseDTO;
import com.nutricore.manager.domain.services.NutritionGoalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/nutrition-goals")
@RequiredArgsConstructor
@Tag(name = "Nutrition Goals", description = "Endpoints para gestão de objetivos nutricionais dos pacientes")
public class NutritionGoalController {

    private final NutritionGoalService service;

    // =========================
    // CREATE
    // =========================
    @Operation(summary = "Cria um novo objetivo nutricional", description = "Cria um objetivo nutricional vinculado a um paciente. O status inicial é definido automaticamente pelo sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Objetivo nutricional criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado")
    })
    @PostMapping
    public ResponseEntity<NutritionGoalResponseDTO> create(
            @RequestBody @Valid NutritionGoalRequestDTO request) {

        NutritionGoalResponseDTO response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // =========================
    // UPDATE
    // =========================
    @Operation(summary = "Atualiza um objetivo nutricional", description = "Atualiza os dados de um objetivo nutricional existente. O status não pode ser alterado diretamente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Objetivo nutricional atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Objetivo nutricional não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<NutritionGoalResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid NutritionGoalRequestDTO request) {

        NutritionGoalResponseDTO response = service.update(id, request);
        return ResponseEntity.ok(response);
    }

    // =========================
    // FIND BY ID
    // =========================
    @Operation(summary = "Busca objetivo nutricional por ID", description = "Retorna os detalhes de um objetivo nutricional específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Objetivo nutricional encontrado"),
            @ApiResponse(responseCode = "404", description = "Objetivo nutricional não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<NutritionGoalResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // =========================
    // DELETE
    // =========================
    @Operation(summary = "Remove um objetivo nutricional", description = "Exclui permanentemente um objetivo nutricional do sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Objetivo nutricional removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Objetivo nutricional não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
