package com.nutricore.manager.api.controllers;

import com.nutricore.manager.api.dto.PatientRequest;
import com.nutricore.manager.api.dto.PatientResponse;
import com.nutricore.manager.api.mappers.PatientEntityConverter;
import com.nutricore.manager.domain.entities.Patient;
import com.nutricore.manager.domain.services.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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
}
