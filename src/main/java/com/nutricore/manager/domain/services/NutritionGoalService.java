package com.nutricore.manager.domain.services;

import com.nutricore.manager.api.dto.NutritionGoalRequestDTO;
import com.nutricore.manager.api.dto.NutritionGoalResponseDTO;
import com.nutricore.manager.api.mappers.NutritionGoalMapper;
import com.nutricore.manager.domain.enums.goal.GoalStatus;
import com.nutricore.manager.domain.exceptions.ResourceNotFoundException;
import com.nutricore.manager.infrastructure.db.repositories.NutritionGoalRepository;
import com.nutricore.manager.infrastructure.db.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NutritionGoalService {

    private final NutritionGoalRepository nutritionGoalRepository;
    private final PatientRepository patientRepository;
    private final NutritionGoalMapper nutritionGoalMapper;

    @Transactional
    public NutritionGoalResponseDTO create(NutritionGoalRequestDTO request) {
        // TODO: Validate patient existence
        var patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Paciente não encontrado com ID: " + request.patientId()));
        // TODO: Map DTO to entity
        var entity = nutritionGoalMapper.toEntity(request);

        // TODO: Set default status/rules
        entity.setPatient(patient);
        entity.setStatus(GoalStatus.PLANNED);

        entity = nutritionGoalRepository.save(entity);
        // TODO: Return response DTO
        return nutritionGoalMapper.toResponse(entity);
    }

    @Transactional
    public NutritionGoalResponseDTO update(Long id, NutritionGoalRequestDTO request) {
        // TODO: Find existing goal by ID or throw exception
        var nutritionGoal = nutritionGoalRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado com ID: " + id));
        // TODO: Update entity using mapper
        nutritionGoalMapper.updateEntityFromDto(request, nutritionGoal);
        // TODO: Validate status transitions if needed
        // TODO: Save updated entity
        nutritionGoal = nutritionGoalRepository.save(nutritionGoal);
        // TODO: Return response DTO
        return nutritionGoalMapper.toResponse(nutritionGoal);
    }

    @Transactional(readOnly = true)
    public NutritionGoalResponseDTO findById(Long id) {
        return nutritionGoalRepository.findById(id)
                .map(nutritionGoalMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado com ID: " + id));
    }

    public Page<NutritionGoalResponseDTO> findAllByPatient(Long patientId, Pageable pageable) {
        // TODO: Validate patient existence (optional, depends on requirement)
        // TODO: Find all goals by patient ID
        // TODO: Map page content to response DTOs
        return null; // Keeping as stub per instructions
    }

    @Transactional
    public void delete(Long id) {
        if (!nutritionGoalRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado com ID: " + id);
        }
        nutritionGoalRepository.deleteById(id);
    }
}
