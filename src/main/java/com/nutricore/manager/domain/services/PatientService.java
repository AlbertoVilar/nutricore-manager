package com.nutricore.manager.domain.services;

import com.nutricore.manager.api.dto.PatientRequest;
import com.nutricore.manager.api.dto.PatientResponse;
import com.nutricore.manager.api.mappers.PatientEntityConverter;
import com.nutricore.manager.domain.entities.Patient;
import com.nutricore.manager.infrastructure.db.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientEntityConverter entityConverter;

    public PatientService(PatientRepository patientRepository, PatientEntityConverter entityConverter) {
        this.patientRepository = patientRepository;
        this.entityConverter = entityConverter;
    }

    // POST
    public PatientResponse createPatient(PatientRequest request) {

        if (request == null) {
            throw new NullPointerException("A entidade não pode ser nula");
        }
        if (patientRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("Já existe um usuário com este e-mail " + request.email());
        }
        var entity = entityConverter.toEntity(request);
            entity = patientRepository.save(entity);

            return entityConverter.toResponse(entity);
    }

    // GET
    // Assinatura para listar todos
    public Page<PatientResponse> findAllPatients(Pageable pageable) {
        Page<Patient> pages = patientRepository.findAll(pageable);
        return  pages.map(entityConverter::toResponse);
    }

    // Assinatura para buscar por ID
    public PatientResponse findPatientById(Long id) {
        return null;
    }
}
