package com.nutricore.manager.domain.services;

import com.nutricore.manager.api.dto.AnthropometricRequestDTO;
import com.nutricore.manager.api.dto.AnthropometricResponseDTO;
import com.nutricore.manager.api.mappers.AnthropometricMapper;
import com.nutricore.manager.domain.entities.AnthropometricAssessment;
import com.nutricore.manager.domain.exceptions.BusinessException;
import com.nutricore.manager.domain.exceptions.ResourceNotFoundException;
import com.nutricore.manager.domain.utils.EnergyCalculator;
import com.nutricore.manager.infrastructure.db.repositories.AnthropometricAssessmentRepository;
import com.nutricore.manager.infrastructure.db.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class AnthropometricService {

    private final AnthropometricAssessmentRepository assessmentRepository;
    private final PatientRepository patientRepository;
    private final AnthropometricMapper mapper;

    public AnthropometricService(AnthropometricAssessmentRepository assessmentRepository,
            PatientRepository patientRepository, AnthropometricMapper mapper) {
        this.assessmentRepository = assessmentRepository;
        this.patientRepository = patientRepository;
        this.mapper = mapper;
    }

    @Transactional
    public AnthropometricResponseDTO create(AnthropometricRequestDTO request) {

        var patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Paciente não encontrado com ID: " + request.patientId()
                ));

        var assessment = mapper.toEntity(request);
        assessment.setPatient(patient);

        // ============================
        // CÁLCULO ENERGÉTICO (ORQUESTRAÇÃO)
        // ============================

        if (assessment.getBasalMetabolicRate() == null) {

            var weight = assessment.getWeight();
            var height = assessment.getHeight();
            var age = patient.calculateAge();
            var gender = patient.getGender();

            if (weight != null && height != null && gender != null && age > 0) {
                var bmr = EnergyCalculator.calculateBmr(
                        weight,
                        height,
                        age,
                        gender
                );
                assessment.setBasalMetabolicRate(bmr);
            }
        }

        if (assessment.getTotalEnergyExpenditure() == null
                && assessment.getBasalMetabolicRate() != null
                && assessment.getActivityLevel() != null) {

            var tdee = EnergyCalculator.calculateTdee(
                    assessment.getBasalMetabolicRate(),
                    assessment.getActivityLevel()
            );
            assessment.setTotalEnergyExpenditure(tdee);
        }

        assessment = assessmentRepository.save(assessment);

        return mapper.toResponse(assessment);
    }


    @Transactional
    public AnthropometricResponseDTO update(Long id, AnthropometricRequestDTO request) {

        var assessment = assessmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Avaliação antropométrica não encontrada com ID: " + id
                ));

        mapper.updateEntityFromDto(request, assessment);

        // ============================
        // CÁLCULO ENERGÉTICO (ORQUESTRAÇÃO)
        // ============================

        var patient = assessment.getPatient();

        if (assessment.getBasalMetabolicRate() == null) {

            var weight = assessment.getWeight();
            var height = assessment.getHeight();
            var age = patient.calculateAge();
            var gender = patient.getGender();

            if (weight != null && height != null && gender != null && age > 0) {
                var bmr = EnergyCalculator.calculateBmr(
                        weight,
                        height,
                        age,
                        gender
                );
                assessment.setBasalMetabolicRate(bmr);
            }
        }

        if (assessment.getTotalEnergyExpenditure() == null
                && assessment.getBasalMetabolicRate() != null
                && assessment.getActivityLevel() != null) {

            var tdee = EnergyCalculator.calculateTdee(
                    assessment.getBasalMetabolicRate(),
                    assessment.getActivityLevel()
            );
            assessment.setTotalEnergyExpenditure(tdee);
        }

        assessment = assessmentRepository.save(assessment);

        return mapper.toResponse(assessment);
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
        // 3. Converter para DTO usando o Mapper (que já aplica as classificações do
        // NutriCalculators)
        return assessments.map(mapper::toResponse);
    }

    @Transactional
    public void delete(Long id) {
        if (!assessmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Avaliação não encontrada com ID: " + id);
        }
        assessmentRepository.deleteById(id);
    }
}
