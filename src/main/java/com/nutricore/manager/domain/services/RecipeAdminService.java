package com.nutricore.manager.domain.services;

import com.nutricore.manager.api.dto.AdminRecipeResponseDTO;
import com.nutricore.manager.api.dto.AdminRecipeUpsertRequestDTO;
import com.nutricore.manager.api.mappers.RecipeAdminMapper;
import com.nutricore.manager.domain.entities.Recipe;
import com.nutricore.manager.domain.enums.editorial.EditorialStatus;
import com.nutricore.manager.domain.exceptions.BusinessException;
import com.nutricore.manager.domain.exceptions.ResourceNotFoundException;
import com.nutricore.manager.domain.utils.SlugGenerator;
import com.nutricore.manager.infrastructure.db.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeAdminService {

    private final RecipeRepository recipeRepository;
    private final RecipeAdminMapper recipeAdminMapper;

    @Transactional(readOnly = true)
    public List<AdminRecipeResponseDTO> findAll(EditorialStatus status) {
        List<Recipe> recipes = status == null
                ? recipeRepository.findAllByOrderByUpdatedAtDesc()
                : recipeRepository.findAllByStatusOrderByUpdatedAtDesc(status);

        return recipes.stream()
                .map(recipeAdminMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public AdminRecipeResponseDTO findById(Long id) {
        return recipeAdminMapper.toResponse(findEntityById(id));
    }

    @Transactional
    public AdminRecipeResponseDTO create(AdminRecipeUpsertRequestDTO request) {
        Recipe entity = recipeAdminMapper.toEntity(request);
        entity.setSlug(resolveSlug(request.slug(), request.title(), null));
        entity.setFeatured(Boolean.TRUE.equals(request.featured()));
        applyEditorialState(entity, request.status(), request.publishedAt());
        return recipeAdminMapper.toResponse(recipeRepository.save(entity));
    }

    @Transactional
    public AdminRecipeResponseDTO update(Long id, AdminRecipeUpsertRequestDTO request) {
        Recipe entity = findEntityById(id);
        recipeAdminMapper.updateEntity(request, entity);
        entity.setSlug(resolveSlug(request.slug(), request.title(), id));
        entity.setFeatured(Boolean.TRUE.equals(request.featured()));
        applyEditorialState(entity, request.status(), request.publishedAt());
        return recipeAdminMapper.toResponse(recipeRepository.save(entity));
    }

    @Transactional
    public AdminRecipeResponseDTO publish(Long id) {
        Recipe entity = findEntityById(id);
        entity.setStatus(EditorialStatus.PUBLISHED);
        if (entity.getPublishedAt() == null) {
            entity.setPublishedAt(LocalDateTime.now());
        }
        return recipeAdminMapper.toResponse(recipeRepository.save(entity));
    }

    @Transactional
    public AdminRecipeResponseDTO moveToDraft(Long id) {
        Recipe entity = findEntityById(id);
        entity.setStatus(EditorialStatus.DRAFT);
        entity.setPublishedAt(null);
        return recipeAdminMapper.toResponse(recipeRepository.save(entity));
    }

    @Transactional
    public AdminRecipeResponseDTO archive(Long id) {
        Recipe entity = findEntityById(id);
        entity.setStatus(EditorialStatus.ARCHIVED);
        entity.setPublishedAt(null);
        return recipeAdminMapper.toResponse(recipeRepository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        Recipe entity = findEntityById(id);
        recipeRepository.delete(entity);
    }

    private Recipe findEntityById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Receita nao encontrada."));
    }

    private String resolveSlug(String requestedSlug, String title, Long currentId) {
        String source = requestedSlug != null && !requestedSlug.isBlank() ? requestedSlug : title;
        String baseSlug = SlugGenerator.slugify(source);

        if (baseSlug.isBlank()) {
            throw new BusinessException("Nao foi possivel gerar um slug valido para a receita.");
        }

        String candidate = baseSlug;
        int suffix = 2;

        while (isSlugInUse(candidate, currentId)) {
            candidate = baseSlug + "-" + suffix++;
        }

        return candidate;
    }

    private boolean isSlugInUse(String slug, Long currentId) {
        return currentId == null
                ? recipeRepository.existsBySlug(slug)
                : recipeRepository.existsBySlugAndIdNot(slug, currentId);
    }

    private void applyEditorialState(Recipe entity, EditorialStatus requestedStatus, LocalDateTime requestedPublishedAt) {
        EditorialStatus status = requestedStatus != null ? requestedStatus : EditorialStatus.DRAFT;
        entity.setStatus(status);

        if (status == EditorialStatus.PUBLISHED) {
            entity.setPublishedAt(requestedPublishedAt != null ? requestedPublishedAt : LocalDateTime.now());
            return;
        }

        entity.setPublishedAt(null);
    }
}
