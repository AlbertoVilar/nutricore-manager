package com.nutricore.manager.api.controllers;

import com.nutricore.manager.api.dto.PublicPlanAdminRequestDTO;
import com.nutricore.manager.api.dto.PublicPlanAdminResponseDTO;
import com.nutricore.manager.domain.services.PublicPlanAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/public-plans")
@Tag(name = "Admin Public Plans", description = "Administração privada dos planos públicos do site")
public class AdminPublicPlanController {

    private final PublicPlanAdminService publicPlanAdminService;

    @GetMapping
    @Operation(summary = "Lista todos os planos públicos")
    public ResponseEntity<List<PublicPlanAdminResponseDTO>> findAll() {
        return ResponseEntity.ok(publicPlanAdminService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um plano público por ID")
    public ResponseEntity<PublicPlanAdminResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(publicPlanAdminService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Cria um novo plano público")
    public ResponseEntity<PublicPlanAdminResponseDTO> create(@Valid @RequestBody PublicPlanAdminRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(publicPlanAdminService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um plano público")
    public ResponseEntity<PublicPlanAdminResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody PublicPlanAdminRequestDTO request
    ) {
        return ResponseEntity.ok(publicPlanAdminService.update(id, request));
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Ativa um plano público")
    public ResponseEntity<PublicPlanAdminResponseDTO> activate(@PathVariable Long id) {
        return ResponseEntity.ok(publicPlanAdminService.activate(id));
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Desativa um plano público")
    public ResponseEntity<PublicPlanAdminResponseDTO> deactivate(@PathVariable Long id) {
        return ResponseEntity.ok(publicPlanAdminService.deactivate(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um plano público")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        publicPlanAdminService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
