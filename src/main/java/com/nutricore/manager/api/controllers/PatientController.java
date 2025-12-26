package com.nutricore.manager.api.controllers;

import com.nutricore.manager.api.dto.PatientRequestDTO;
import com.nutricore.manager.api.dto.PatientResponseDTO;
import com.nutricore.manager.domain.exceptions.error.StandardError;
import com.nutricore.manager.domain.services.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Pacientes", description = "Gerenciamento do cadastro de pacientes")
@RestController
@RequestMapping("/v1/patients")
public class PatientController {

        private final PatientService patientService;

        public PatientController(PatientService patientService) {
                this.patientService = patientService;
        }

        // POST
        @Operation(summary = "Cria um novo paciente", description = "Cadastra um novo paciente no sistema com validação de dados.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Paciente criado com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Violação de regra de negócio ou erro de sintaxe", content = @Content(schema = @Schema(implementation = StandardError.class))),
                        @ApiResponse(responseCode = "422", description = "Erro de validação nos campos (Bean Validation)", content = @Content(schema = @Schema(implementation = StandardError.class)))
        })
        @PostMapping
        public ResponseEntity<PatientResponseDTO> createPatient(@RequestBody @Valid PatientRequestDTO request) {
                PatientResponseDTO patientResponseDTO = patientService.createPatient(request);
                return ResponseEntity.status(HttpStatus.CREATED).body(patientResponseDTO);
        }

        // PUT
        @Operation(summary = "Atualiza um paciente", description = "Atualiza os dados de um paciente existente com validação de dados.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Paciente atualizado com sucesso"),
                        @ApiResponse(responseCode = "400", description = "Violação de regra de negócio ou erro de sintaxe", content = @Content(schema = @Schema(implementation = StandardError.class))),
                        @ApiResponse(responseCode = "422", description = "Erro de validação nos campos (Bean Validation)", content = @Content(schema = @Schema(implementation = StandardError.class))),
                        @ApiResponse(responseCode = "404", description = "Paciente não encontrado", content = @Content(schema = @Schema(implementation = StandardError.class)))
        })
        @PutMapping("/{id}")
        public ResponseEntity<PatientResponseDTO> updatePatient(@PathVariable Long id,
                        @RequestBody @Valid PatientRequestDTO request) {
                PatientResponseDTO patientResponseDTO = patientService.updatePatient(id, request);
                return ResponseEntity.ok(patientResponseDTO);
        }

        // GET /patients/{id}
        @Operation(summary = "Busca um paciente por ID", description = "Retorna os detalhes de um paciente específico.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
                        @ApiResponse(responseCode = "404", description = "Paciente não encontrado", content = @Content(schema = @Schema(implementation = StandardError.class)))
        })
        @GetMapping("/{id}")
        public ResponseEntity<PatientResponseDTO> getById(@PathVariable Long id) {
                PatientResponseDTO patientResponseDTO = patientService.findPatientById(id);
                return ResponseEntity.ok(patientResponseDTO);
        }

        // GET /patients
        @Operation(summary = "Lista todos os pacientes", description = "Retorna uma lista paginada de todos os pacientes cadastrados.")
        @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso")
        @GetMapping
        public ResponseEntity<Page<PatientResponseDTO>> getAll(
                        @ParameterObject // Melhora a visualização no Swagger
                        @PageableDefault(size = 12) Pageable pageable) {

                Page<PatientResponseDTO> patients = patientService.findAllPatients(pageable);
                return ResponseEntity.ok(patients);
        }

        // DELETE /patients/{id}
        @Operation(summary = "Remove um paciente", description = "Exclui permanentemente um paciente do sistema.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Paciente removido com sucesso (Sem conteúdo)"),
                        @ApiResponse(responseCode = "404", description = "Paciente não encontrado", content = @Content(schema = @Schema(implementation = StandardError.class))),
                        @ApiResponse(responseCode = "400", description = "Erro de integridade (ex: paciente possui vínculos)", content = @Content(schema = @Schema(implementation = StandardError.class)))
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
                patientService.deletePatient(id);
                return ResponseEntity.noContent().build();
        }
}