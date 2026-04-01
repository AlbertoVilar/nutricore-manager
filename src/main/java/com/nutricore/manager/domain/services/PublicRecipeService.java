package com.nutricore.manager.domain.services;

import com.nutricore.manager.api.dto.PublicRecipeResponseDTO;
import com.nutricore.manager.api.mappers.PublicRecipeMapper;
import com.nutricore.manager.infrastructure.db.repositories.PublicRecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicRecipeService {

    private final PublicRecipeRepository publicRecipeRepository;
    private final PublicRecipeMapper publicRecipeMapper;

    @Transactional(readOnly = true)
    public List<PublicRecipeResponseDTO> findAll() {
        return publicRecipeRepository.findAllByOrderByDisplayOrderAsc().stream()
                .map(publicRecipeMapper::toResponse)
                .toList();
    }
}
