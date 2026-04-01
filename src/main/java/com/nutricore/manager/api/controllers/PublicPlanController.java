package com.nutricore.manager.api.controllers;

import com.nutricore.manager.api.dto.PublicPlanResponseDTO;
import com.nutricore.manager.domain.services.PublicPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/public/plans")
@Tag(name = "Public Plans", description = "Planos publicos exibidos no site")
public class PublicPlanController {

    private final PublicPlanService publicPlanService;

    @GetMapping
    @Operation(summary = "Lista os planos publicos")
    public ResponseEntity<List<PublicPlanResponseDTO>> findAll() {
        return ResponseEntity.ok(publicPlanService.findAll());
    }
}
