package com.nutricore.manager.api.controllers;

import com.nutricore.manager.api.dto.PublicRecipeResponseDTO;
import com.nutricore.manager.domain.services.PublicRecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/public/recipes")
@Tag(name = "Public Recipes", description = "Receitas e dicas publicas do site")
public class PublicRecipeController {

    private final PublicRecipeService publicRecipeService;

    @GetMapping
    @Operation(summary = "Lista as receitas publicas")
    public ResponseEntity<List<PublicRecipeResponseDTO>> findAll() {
        return ResponseEntity.ok(publicRecipeService.findAllPublished());
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Busca uma receita publicada pelo slug")
    public ResponseEntity<PublicRecipeResponseDTO> findBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(publicRecipeService.findBySlug(slug));
    }
}
