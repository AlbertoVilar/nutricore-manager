package com.nutricore.manager.domain.services;

import com.nutricore.manager.api.dto.ClinicalAnamnesisRequestDTO;
import com.nutricore.manager.api.dto.ClinicalAnamnesisResponseDTO;
import com.nutricore.manager.api.mappers.ClinicalAnamnesisMapper;
import com.nutricore.manager.domain.enums.anamneses.BowelFunction;
import com.nutricore.manager.domain.enums.anamneses.SleepQuality;
import com.nutricore.manager.domain.exceptions.BusinessException;
import com.nutricore.manager.domain.exceptions.ResourceNotFoundException;
import com.nutricore.manager.infrastructure.db.repositories.ClinicalAnamnesisRepository;
import com.nutricore.manager.infrastructure.db.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ClinicalAnamnesisService {

    private final ClinicalAnamnesisRepository anamnesisRepository;
    private final PatientRepository patientRepository;
    private final ClinicalAnamnesisMapper anamnesisMapper;

    public ClinicalAnamnesisService(ClinicalAnamnesisRepository anamnesisRepository,
                                    PatientRepository patientRepository,
                                    ClinicalAnamnesisMapper anamnesisMapper) {
        this.anamnesisRepository = anamnesisRepository;
        this.patientRepository = patientRepository;
        this.anamnesisMapper = anamnesisMapper;
    }

    @Transactional
    public ClinicalAnamnesisResponseDTO createAnamnesis(ClinicalAnamnesisRequestDTO request) {
        var patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com ID: "
                        + request.patientId()));

        var anamnesis = anamnesisMapper.toEntity(request);
        patient.addAnamnesis(anamnesis);

        anamnesis = anamnesisRepository.save(anamnesis);
        return anamnesisMapper.toResponse(anamnesis);
    }

    @Transactional
    public ClinicalAnamnesisResponseDTO updateAnamnesis(Long id, ClinicalAnamnesisRequestDTO request) {

        var anamnesis = anamnesisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anamnese não encontrada com ID: " + id));

        anamnesisMapper.updateEntityFromRequest(request, anamnesis);
        // O Hibernate fará o update automático ao fim do método por estar em contexto @Transactional
        return anamnesisMapper.toResponse(anamnesis);
    }

    @Transactional(readOnly = true)
    public Page<ClinicalAnamnesisResponseDTO> getHistory(
            Long patientId,
            LocalDate start,
            LocalDate end,
            SleepQuality sleep,
            BowelFunction bowel,
            Pageable pageable) {

        // Garante que o paciente existe antes de buscar
        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Paciente não encontrado com ID: " + patientId);
        }

        return anamnesisRepository.findByPatientIdWithFilters(
                        patientId, start, end, sleep, bowel, pageable)
                .map(anamnesisMapper::toResponse);
    }

    @Transactional
    public void deleteAnamnesis(Long id) {
        if (!anamnesisRepository.existsById(id)) {
            throw new ResourceNotFoundException("Anamnese não encontrada com ID: " + id);
        }
        anamnesisRepository.deleteById(id);
    }
}