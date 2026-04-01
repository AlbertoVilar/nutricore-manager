package com.nutricore.manager.api.controllers;

import com.nutricore.manager.api.dto.PublicPostResponseDTO;
import com.nutricore.manager.domain.services.PublicPostService;
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
@RequestMapping("/v1/public/posts")
@Tag(name = "Public Posts", description = "Conteudos publicos do site")
public class PublicPostController {

    private final PublicPostService publicPostService;

    @GetMapping
    @Operation(summary = "Lista os conteudos publicos")
    public ResponseEntity<List<PublicPostResponseDTO>> findAll() {
        return ResponseEntity.ok(publicPostService.findAll());
    }
}
