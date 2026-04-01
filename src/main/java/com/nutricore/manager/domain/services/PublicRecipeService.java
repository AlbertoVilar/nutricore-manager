package com.nutricore.manager.domain.services;

import com.nutricore.manager.api.dto.PublicRecipeResponseDTO;
import com.nutricore.manager.api.mappers.PublicRecipeMapper;
import com.nutricore.manager.domain.enums.editorial.EditorialStatus;
import com.nutricore.manager.domain.exceptions.ResourceNotFoundException;
import com.nutricore.manager.infrastructure.db.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicRecipeService {

    private final RecipeRepository recipeRepository;
    private final PublicRecipeMapper publicRecipeMapper;

    @Transactional(readOnly = true)
    public List<PublicRecipeResponseDTO> findAllPublished() {
        return recipeRepository.findAllByStatusOrderByPublishedAtDesc(EditorialStatus.PUBLISHED).stream()
                .map(publicRecipeMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PublicRecipeResponseDTO findBySlug(String slug) {
        return recipeRepository.findBySlugAndStatus(slug, EditorialStatus.PUBLISHED)
                .map(publicRecipeMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Receita pública não encontrada."));
    }
}
