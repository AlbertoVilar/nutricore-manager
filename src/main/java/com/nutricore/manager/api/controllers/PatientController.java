package com.nutricore.manager.api.controllers;

import com.nutricore.manager.api.dto.PatientRequest;
import com.nutricore.manager.api.dto.PatientResponse;
import com.nutricore.manager.api.mappers.PatientEntityConverter;
import com.nutricore.manager.domain.entities.Patient;
import com.nutricore.manager.domain.services.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService, PatientEntityConverter entityConverter) {
        this.patientService = patientService;
    }

    //POST
    @PostMapping
    public ResponseEntity<PatientResponse> creatPatient(@RequestBody PatientRequest request) {
        if (request == null) {
            return ResponseEntity.badRequest().build();
        }
        PatientResponse patientResponse = patientService.createPatient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(patientResponse);
    }

    // GET /patients
    @GetMapping
    public ResponseEntity<Page<PatientResponse>> getAll(
                                        @PageableDefault(size = 12)Pageable pageable) {
        Page<PatientResponse> patients = patientService.findAllPatients(pageable);
        return ResponseEntity.ok(patients);
    }

    // GET /patients/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getById(@PathVariable Long id) {
        return null;
    }

}
