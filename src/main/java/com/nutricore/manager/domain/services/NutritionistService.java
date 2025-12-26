package com.nutricore.manager.domain.services;

import com.nutricore.manager.api.dto.NutritionistRequestDTO;
import com.nutricore.manager.api.dto.NutritionistResponseDTO;
import com.nutricore.manager.api.mappers.NutritionistMapper;
import com.nutricore.manager.domain.entities.Nutritionist;
import com.nutricore.manager.domain.exceptions.DatabaseException;
import com.nutricore.manager.domain.exceptions.ResourceNotFoundException;
import com.nutricore.manager.infrastructure.db.repositories.NutritionistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NutritionistService {

    private final NutritionistRepository repository;
    private final NutritionistMapper mapper;

    @Transactional
    public NutritionistResponseDTO create(NutritionistRequestDTO request) {
        Nutritionist entity = mapper.toEntity(request);
        Nutritionist saved = repository.save(entity);
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Page<NutritionistResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public NutritionistResponseDTO findById(Long id) {
        Nutritionist entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nutritionist not found with ID: " + id));
        return mapper.toResponse(entity);
    }

    @Transactional
    public NutritionistResponseDTO update(Long id, NutritionistRequestDTO request) {
        Nutritionist entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nutritionist not found with ID: " + id));

        mapper.updateEntityFromDto(request, entity);
        Nutritionist updated = repository.save(entity);
        return mapper.toResponse(updated);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Nutritionist not found with ID: " + id);
        }
        repository.deleteById(id);
    }
}
