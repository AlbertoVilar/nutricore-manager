package com.nutricore.manager.api.controllers;

import com.nutricore.manager.api.dto.AdminArticleResponseDTO;
import com.nutricore.manager.api.dto.AdminArticleUpsertRequestDTO;
import com.nutricore.manager.domain.enums.editorial.EditorialStatus;
import com.nutricore.manager.domain.services.ArticleAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/articles")
@Tag(name = "Admin Articles", description = "Gestao editorial de artigos")
public class AdminArticleController {

    private final ArticleAdminService articleAdminService;

    @GetMapping
    @Operation(summary = "Lista artigos para administracao")
    public ResponseEntity<List<AdminArticleResponseDTO>> findAll(
            @RequestParam(required = false) EditorialStatus status
    ) {
        return ResponseEntity.ok(articleAdminService.findAll(status));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um artigo pelo id para edicao")
    public ResponseEntity<AdminArticleResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(articleAdminService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Cria um novo artigo")
    public ResponseEntity<AdminArticleResponseDTO> create(@Valid @RequestBody AdminArticleUpsertRequestDTO request) {
        AdminArticleResponseDTO response = articleAdminService.create(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um artigo existente")
    public ResponseEntity<AdminArticleResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody AdminArticleUpsertRequestDTO request
    ) {
        return ResponseEntity.ok(articleAdminService.update(id, request));
    }

    @PatchMapping("/{id}/publish")
    @Operation(summary = "Publica um artigo")
    public ResponseEntity<AdminArticleResponseDTO> publish(@PathVariable Long id) {
        return ResponseEntity.ok(articleAdminService.publish(id));
    }

    @PatchMapping("/{id}/draft")
    @Operation(summary = "Move um artigo para rascunho")
    public ResponseEntity<AdminArticleResponseDTO> moveToDraft(@PathVariable Long id) {
        return ResponseEntity.ok(articleAdminService.moveToDraft(id));
    }

    @PatchMapping("/{id}/archive")
    @Operation(summary = "Arquiva um artigo")
    public ResponseEntity<AdminArticleResponseDTO> archive(@PathVariable Long id) {
        return ResponseEntity.ok(articleAdminService.archive(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um artigo")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleAdminService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
