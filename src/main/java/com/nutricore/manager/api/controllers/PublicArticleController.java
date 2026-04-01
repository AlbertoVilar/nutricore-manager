package com.nutricore.manager.api.controllers;

import com.nutricore.manager.api.dto.PublicArticleResponseDTO;
import com.nutricore.manager.domain.services.ArticlePublicService;
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
@RequestMapping("/v1/public/articles")
@Tag(name = "Artigos públicos", description = "Artigos publicados do site")
public class PublicArticleController {

    private final ArticlePublicService articlePublicService;

    @GetMapping
    @Operation(summary = "Lista os artigos publicados")
    public ResponseEntity<List<PublicArticleResponseDTO>> findAll() {
        return ResponseEntity.ok(articlePublicService.findAllPublished());
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Busca um artigo publicado pelo slug")
    public ResponseEntity<PublicArticleResponseDTO> findBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(articlePublicService.findBySlug(slug));
    }
}
