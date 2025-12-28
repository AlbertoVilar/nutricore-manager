package com.nutricore.manager.api.controllers;

import com.nutricore.manager.api.dto.MealPlanResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/meal-plans")
public class MealPlanController {

    @PostMapping
    public ResponseEntity<MealPlanResponseDTO> create(@RequestBody Object request) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping("/active/patient/{patientId}")
    public ResponseEntity<MealPlanResponseDTO> getActiveByPatient(@PathVariable Long patientId) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PatchMapping("/deactivate/patient/{patientId}")
    public ResponseEntity<Void> deactivate(@PathVariable Long patientId) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
