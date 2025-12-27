package com.nutricore.manager.api.controllers;

import com.nutricore.manager.api.dto.NutritionGoalRequestDTO;
import com.nutricore.manager.api.dto.NutritionGoalResponseDTO;
import com.nutricore.manager.domain.services.NutritionGoalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/nutrition-goals")
@RequiredArgsConstructor
public class NutritionGoalController {

    private final NutritionGoalService service;

    @PostMapping
    public ResponseEntity<NutritionGoalResponseDTO> create(@RequestBody @Valid NutritionGoalRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NutritionGoalResponseDTO> update(@PathVariable Long id,
            @RequestBody @Valid NutritionGoalRequestDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<NutritionGoalResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<Page<NutritionGoalResponseDTO>> findAllByPatient(@PathVariable Long patientId,
            Pageable pageable) {
        return ResponseEntity.ok(service.findAllByPatient(patientId, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
