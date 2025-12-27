package com.nutricore.manager.api.controllers;

import com.nutricore.manager.api.dto.NutritionGoalRequestDTO;
import com.nutricore.manager.api.dto.NutritionGoalResponseDTO;
import com.nutricore.manager.domain.exceptions.error.StandardError;
import com.nutricore.manager.domain.services.NutritionGoalService;
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
@RequestMapping("/api/v1/nutrition-goals")
@RequiredArgsConstructor
@Tag(name = "Nutrition Goals", description = "Endpoints for managing nutrition goals")
public class NutritionGoalController {

    private final NutritionGoalService service;

    @Operation(summary = "Create a new nutrition goal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Nutrition goal created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @PostMapping
    public ResponseEntity<NutritionGoalResponseDTO> create(@RequestBody @Valid NutritionGoalRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @Operation(summary = "Update a nutrition goal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nutrition goal updated successfully"),
            @ApiResponse(responseCode = "404", description = "Nutrition goal not found", content = @Content(schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<NutritionGoalResponseDTO> update(@PathVariable Long id,
            @RequestBody @Valid NutritionGoalRequestDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Find nutrition goal by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nutrition goal found"),
            @ApiResponse(responseCode = "404", description = "Nutrition goal not found", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<NutritionGoalResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "List all nutrition goals by patient")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<Page<NutritionGoalResponseDTO>> findAllByPatient(
            @PathVariable Long patientId,
            @ParameterObject @PageableDefault(size = 12) Pageable pageable) {
        return ResponseEntity.ok(service.findAllByPatient(patientId, pageable));
    }

    @Operation(summary = "Delete a nutrition goal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nutrition goal deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Nutrition goal not found", content = @Content(schema = @Schema(implementation = StandardError.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
