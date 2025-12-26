package com.nutricore.manager.domain.services;

import com.nutricore.manager.api.dto.AnthropometricRequestDTO;
import com.nutricore.manager.api.dto.AnthropometricResponseDTO;
import com.nutricore.manager.api.mappers.AnthropometricMapper;
import com.nutricore.manager.domain.entities.AnthropometricAssessment;
import com.nutricore.manager.domain.entities.Patient;
import com.nutricore.manager.domain.exceptions.BusinessException;
import com.nutricore.manager.domain.exceptions.ResourceNotFoundException;
import com.nutricore.manager.infrastructure.db.repositories.AnthropometricAssessmentRepository;
import com.nutricore.manager.infrastructure.db.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com ID: " + request.patientId()));

        // 2. Converter DTO para Entidade
        AnthropometricAssessment assessment = mapper.toEntity(request);

        // 3. Vincular o paciente encontrado à avaliação
        assessment.setPatient(patient);

        // 4. Salvar (O @PrePersist da entidade executará os cálculos de IMC, RCQ, etc.)
        AnthropometricAssessment savedAssessment = assessmentRepository.save(assessment);

        // 5. Converter a entidade salva (já com cálculos) para o ResponseDTO
        return mapper.toResponse(savedAssessment);
    }

    @Transactional(readOnly = true)
    public Page<AnthropometricResponseDTO> findAllByPatientId(Long patientId, Pageable pageable) {

        if (patientId == null) {
            throw new BusinessException("O ID do paciente é obrigatório");
        }
        // 1. Verificar se o paciente existe (opcional, mas boa prática para lançar 404)
       patientRepository.findById(patientId).orElseThrow(
               () -> new ResourceNotFoundException("Paciente não encontrado com ID: " + patientId));
        // 2. Buscar no repository usando a ordenação
        Page<AnthropometricAssessment> assessments = assessmentRepository
                .findByPatientIdOrderByAssessmentDateDesc(patientId, pageable);
        // 3. Converter para DTO usando o Mapper (que já aplica as classificações do NutriCalculators)
        return assessments.map(mapper::toResponse);
    }
}
