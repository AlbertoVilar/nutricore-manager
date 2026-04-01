package com.nutricore.manager.api.controllers;

import com.nutricore.manager.api.dto.AdminRecipeResponseDTO;
import com.nutricore.manager.api.dto.AdminRecipeUpsertRequestDTO;
import com.nutricore.manager.domain.enums.editorial.EditorialStatus;
import com.nutricore.manager.domain.services.RecipeAdminService;
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
@RequestMapping("/v1/admin/recipes")
@Tag(name = "Admin Recipes", description = "Gestao editorial de receitas")
public class AdminRecipeController {

    private final RecipeAdminService recipeAdminService;

    @GetMapping
    @Operation(summary = "Lista receitas para administracao")
    public ResponseEntity<List<AdminRecipeResponseDTO>> findAll(
            @RequestParam(required = false) EditorialStatus status
    ) {
        return ResponseEntity.ok(recipeAdminService.findAll(status));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma receita pelo id para edicao")
    public ResponseEntity<AdminRecipeResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(recipeAdminService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Cria uma nova receita")
    public ResponseEntity<AdminRecipeResponseDTO> create(@Valid @RequestBody AdminRecipeUpsertRequestDTO request) {
        AdminRecipeResponseDTO response = recipeAdminService.create(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma receita existente")
    public ResponseEntity<AdminRecipeResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody AdminRecipeUpsertRequestDTO request
    ) {
        return ResponseEntity.ok(recipeAdminService.update(id, request));
    }

    @PatchMapping("/{id}/publish")
    @Operation(summary = "Publica uma receita")
    public ResponseEntity<AdminRecipeResponseDTO> publish(@PathVariable Long id) {
        return ResponseEntity.ok(recipeAdminService.publish(id));
    }

    @PatchMapping("/{id}/draft")
    @Operation(summary = "Move uma receita para rascunho")
    public ResponseEntity<AdminRecipeResponseDTO> moveToDraft(@PathVariable Long id) {
        return ResponseEntity.ok(recipeAdminService.moveToDraft(id));
    }

    @PatchMapping("/{id}/archive")
    @Operation(summary = "Arquiva uma receita")
    public ResponseEntity<AdminRecipeResponseDTO> archive(@PathVariable Long id) {
        return ResponseEntity.ok(recipeAdminService.archive(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma receita")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recipeAdminService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
