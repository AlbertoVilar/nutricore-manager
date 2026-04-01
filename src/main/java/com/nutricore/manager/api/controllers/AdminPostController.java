package com.nutricore.manager.api.controllers;

import com.nutricore.manager.api.dto.AdminPostResponseDTO;
import com.nutricore.manager.api.dto.AdminPostUpsertRequestDTO;
import com.nutricore.manager.domain.enums.editorial.EditorialStatus;
import com.nutricore.manager.domain.services.PostAdminService;
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
@RequestMapping("/v1/admin/posts")
@Tag(name = "Admin posts", description = "Gestão editorial de posts")
public class AdminPostController {

    private final PostAdminService postAdminService;

    @GetMapping
    @Operation(summary = "Lista posts para administração")
    public ResponseEntity<List<AdminPostResponseDTO>> findAll(
            @RequestParam(required = false) EditorialStatus status
    ) {
        return ResponseEntity.ok(postAdminService.findAll(status));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um post pelo id para edição")
    public ResponseEntity<AdminPostResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(postAdminService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Cria um novo post")
    public ResponseEntity<AdminPostResponseDTO> create(@Valid @RequestBody AdminPostUpsertRequestDTO request) {
        AdminPostResponseDTO response = postAdminService.create(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um post existente")
    public ResponseEntity<AdminPostResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody AdminPostUpsertRequestDTO request
    ) {
        return ResponseEntity.ok(postAdminService.update(id, request));
    }

    @PatchMapping("/{id}/publish")
    @Operation(summary = "Publica um post")
    public ResponseEntity<AdminPostResponseDTO> publish(@PathVariable Long id) {
        return ResponseEntity.ok(postAdminService.publish(id));
    }

    @PatchMapping("/{id}/draft")
    @Operation(summary = "Move um post para rascunho")
    public ResponseEntity<AdminPostResponseDTO> moveToDraft(@PathVariable Long id) {
        return ResponseEntity.ok(postAdminService.moveToDraft(id));
    }

    @PatchMapping("/{id}/archive")
    @Operation(summary = "Arquiva um post")
    public ResponseEntity<AdminPostResponseDTO> archive(@PathVariable Long id) {
        return ResponseEntity.ok(postAdminService.archive(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um post")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postAdminService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
