package com.nutricore.manager.domain.services;

import com.nutricore.manager.api.dto.AnthropometricRequestDTO;
import com.nutricore.manager.api.dto.AnthropometricResponseDTO;
import com.nutricore.manager.api.mappers.AnthropometricMapper;
import com.nutricore.manager.domain.entities.AnthropometricAssessment;
import com.nutricore.manager.domain.entities.Patient;
import com.nutricore.manager.infrastructure.db.repositories.AnthropometricAssessmentRepository;
import com.nutricore.manager.infrastructure.db.repositories.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnthropometricService {

    private final AnthropometricAssessmentRepository assessmentRepository;
    private final PatientRepository patientRepository;
    private final AnthropometricMapper mapper;

    public AnthropometricService(AnthropometricAssessmentRepository assessmentRepository, PatientRepository patientRepository, AnthropometricMapper mapper) {
        this.assessmentRepository = assessmentRepository;
        this.patientRepository = patientRepository;
        this.mapper = mapper;
    }

    @Transactional
    public AnthropometricResponseDTO create(AnthropometricRequestDTO request) {
        // 1. Validar se o paciente existe
        Patient patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new EntityNotFoundException("Paciente não encontrado com ID: " + request.patientId()));

        // 2. Converter DTO para Entidade
        AnthropometricAssessment assessment = mapper.toEntity(request);

        // 3. Vincular o paciente encontrado à avaliação
        assessment.setPatient(patient);

        // 4. Salvar (O @PrePersist da entidade executará os cálculos de IMC, RCQ, etc.)
        AnthropometricAssessment savedAssessment = assessmentRepository.save(assessment);

        // 5. Converter a entidade salva (já com cálculos) para o ResponseDTO
        return mapper.toResponse(savedAssessment);
    }
}
