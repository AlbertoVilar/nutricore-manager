package com.nutricore.manager.api.controllers;

import com.nutricore.manager.api.dto.PublicPostResponseDTO;
import com.nutricore.manager.domain.services.PublicPostService;
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
@RequestMapping("/v1/public/posts")
@Tag(name = "Posts públicos", description = "Conteúdos públicos do site")
public class PublicPostController {

    private final PublicPostService publicPostService;

    @GetMapping
    @Operation(summary = "Lista os posts publicados")
    public ResponseEntity<List<PublicPostResponseDTO>> findAll() {
        return ResponseEntity.ok(publicPostService.findAllPublished());
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Busca um post publicado pelo slug")
    public ResponseEntity<PublicPostResponseDTO> findBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(publicPostService.findBySlug(slug));
    }
}
