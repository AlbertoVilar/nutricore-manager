package com.nutricore.manager.api.controllers;

import com.nutricore.manager.api.dto.AnthropometricRequestDTO;
import com.nutricore.manager.api.dto.AnthropometricResponseDTO;

import com.nutricore.manager.domain.services.AnthropometricService;
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
@RequestMapping("/anthropometric")
@RequiredArgsConstructor
@Tag(name = "Anthropometric Assessment", description = "Endpoints para gestão de avaliações antropométricas")
public class AnthropometricController {

    private final AnthropometricService service;

    @PostMapping
    @Operation(summary = "Cadastra uma nova avaliação antropométrica",
            description = "Realiza o cálculo automático de IMC, RCQ e composição corporal antes de salvar.")
    public ResponseEntity<AnthropometricResponseDTO> create(@RequestBody @Valid AnthropometricRequestDTO request) {
        AnthropometricResponseDTO response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}