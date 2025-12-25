package com.nutricore.manager.domain.services;

import com.nutricore.manager.api.dto.ClinicalAnamnesisRequest;
import com.nutricore.manager.api.dto.ClinicalAnamnesisResponse;
import com.nutricore.manager.api.mappers.ClinicalAnamnesisMapper;
import com.nutricore.manager.domain.exceptions.BusinessException;
import com.nutricore.manager.domain.exceptions.ResourceNotFoundException;
import com.nutricore.manager.infrastructure.db.repositories.ClinicalAnamnesisRepository;
import com.nutricore.manager.infrastructure.db.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ClinicalAnamnesisResponse createAnamnesis(ClinicalAnamnesisRequest request) {
        var patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com ID: "
                        + request.patientId()));

        var anamnesis = anamnesisMapper.toEntity(request);
        patient.addAnamnesis(anamnesis);

        anamnesis = anamnesisRepository.save(anamnesis);
        return anamnesisMapper.toResponse(anamnesis);
    }

    @Transactional
    public ClinicalAnamnesisResponse updateAnamnesis(Long id, ClinicalAnamnesisRequest request) {
        if (id == null) {
            throw new BusinessException("O ID da anamnese é obrigatório");
        }

        var anamnesis = anamnesisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anamnese não encontrada com ID: " + id));

        anamnesisMapper.updateEntityFromRequest(request, anamnesis);
        // O Hibernate fará o update automático ao fim do método por estar em contexto @Transactional
        return anamnesisMapper.toResponse(anamnesis);
    }

    @Transactional(readOnly = true)
    public Page<ClinicalAnamnesisResponse> getHistoryByPatientId(Long patientId, Pageable pageable) {
        if (patientId == null) {
            throw new BusinessException("O ID do paciente é obrigatório");
        }

        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Paciente não encontrado com ID: " + patientId);
        }

        return anamnesisRepository.findByPatientIdOrderByDateDesc(patientId, pageable)
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