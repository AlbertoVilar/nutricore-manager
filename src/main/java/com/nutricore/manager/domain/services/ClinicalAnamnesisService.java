package com.nutricore.manager.domain.services;

import com.nutricore.manager.api.dto.ClinicalAnamnesisRequest;
import com.nutricore.manager.api.dto.ClinicalAnamnesisResponse;
import com.nutricore.manager.api.mappers.ClinicalAnamnesisMapper;
import com.nutricore.manager.domain.entities.ClinicalAnamnesis;
import com.nutricore.manager.domain.exceptions.BusinessException;
import com.nutricore.manager.domain.exceptions.ResourceNotFoundException;
import com.nutricore.manager.infrastructure.db.repositories.ClinicalAnamnesisRepository;
import com.nutricore.manager.infrastructure.db.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClinicalAnamnesisService {

    private final ClinicalAnamnesisRepository anamnesisRepository;
    private final PatientRepository patientRepository;
    private final ClinicalAnamnesisMapper anamnesisMapper;

    public ClinicalAnamnesisService(ClinicalAnamnesisRepository anamnesisRepository, PatientRepository patientRepository, ClinicalAnamnesisMapper anamnesisMapper) {
        this.anamnesisRepository = anamnesisRepository;
        this.patientRepository = patientRepository;
        this.anamnesisMapper = anamnesisMapper;
    }

    // Método para criar uma nova anamnese
    public ClinicalAnamnesisResponse createAnamnesis(ClinicalAnamnesisRequest request) {

        var patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente não encontrado com ID: "
                        + request.patientId()));

        var anamnesi = anamnesisMapper.toEntity(request);

        patient.addAnamnesis(anamnesi);
                anamnesi = anamnesisRepository.save(anamnesi);

                return anamnesisMapper.toResponse(anamnesi);
    }

    // Método para obter a história clínica de um paciente
    public List<ClinicalAnamnesisResponse> getHistoryByPatientId(Long patientId) {

        if (patientId == null) {
            throw new BusinessException("O ID do paciente é obrigatório" + patientId);

        }
        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Paciente não encontrado com ID: " + patientId);
        }

        List<ClinicalAnamnesis> clinicalAnamneses = anamnesisRepository
                .findByPatientIdOrderByDateDesc(patientId);

        return anamnesisMapper.toResponseList(clinicalAnamneses);
    }
}
