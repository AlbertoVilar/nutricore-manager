package com.nutricore.manager.api.controllers;

import com.nutricore.manager.api.dto.PatientRequest;
import com.nutricore.manager.api.dto.PatientResponse;
import com.nutricore.manager.domain.services.PatientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // POST
    @PostMapping
    public ResponseEntity<PatientResponse> createPatient(@RequestBody @Valid PatientRequest request) {
        PatientResponse patientResponse = patientService.createPatient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(patientResponse);
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable Long id, @RequestBody @Valid PatientRequest request) {
        PatientResponse patientResponse = patientService.updatePatient(id, request);
        return ResponseEntity.ok(patientResponse);
    }

    // GET /patients
    @GetMapping
    public ResponseEntity<Page<PatientResponse>> getAll(
            @PageableDefault(size = 12) Pageable pageable) {
        Page<PatientResponse> patients = patientService.findAllPatients(pageable);
        return ResponseEntity.ok(patients);
    }

    // GET /patients/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getById(@PathVariable Long id) {
        PatientResponse patientResponse = patientService.findPatientById(id);
        return ResponseEntity.ok(patientResponse);
    }

    // DELETE /patients/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}