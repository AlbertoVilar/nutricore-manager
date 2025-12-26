package com.nutricore.manager.api.controllers;

import com.nutricore.manager.api.dto.NutritionistRequestDTO;
import com.nutricore.manager.api.dto.NutritionistResponseDTO;
import com.nutricore.manager.domain.exceptions.error.StandardError;
import com.nutricore.manager.domain.services.NutritionistService;
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
@RequestMapping("/v1/nutritionists")
@RequiredArgsConstructor
@Tag(name = "Nutritionists", description = "Endpoints for managing nutritionists")
public class NutritionistController {

    private final NutritionistService service;

    @Operation(summary = "Create a new nutritionist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Nutritionist created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<NutritionistResponseDTO> create(@RequestBody @Valid NutritionistRequestDTO request) {
        NutritionistResponseDTO response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "List all nutritionists")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    @GetMapping
    public ResponseEntity<Page<NutritionistResponseDTO>> findAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @Operation(summary = "Find nutritionist by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nutritionist found"),
            @ApiResponse(responseCode = "404", description = "Nutritionist not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<NutritionistResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Update a nutritionist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Nutritionist updated successfully"),
            @ApiResponse(responseCode = "404", description = "Nutritionist not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<NutritionistResponseDTO> update(@PathVariable Long id,
            @RequestBody @Valid NutritionistRequestDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Operation(summary = "Delete a nutritionist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Nutritionist deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Nutritionist not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
