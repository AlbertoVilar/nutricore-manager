package com.nutricore.manager.domain.services;

import com.nutricore.manager.api.dto.AdminArticleResponseDTO;
import com.nutricore.manager.api.dto.AdminArticleUpsertRequestDTO;
import com.nutricore.manager.api.mappers.ArticleAdminMapper;
import com.nutricore.manager.domain.entities.Article;
import com.nutricore.manager.domain.enums.editorial.EditorialStatus;
import com.nutricore.manager.domain.exceptions.BusinessException;
import com.nutricore.manager.domain.exceptions.ResourceNotFoundException;
import com.nutricore.manager.domain.utils.ReadTimeCalculator;
import com.nutricore.manager.domain.utils.SlugGenerator;
import com.nutricore.manager.infrastructure.db.repositories.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleAdminService {

    private final ArticleRepository articleRepository;
    private final ArticleAdminMapper articleAdminMapper;

    @Transactional(readOnly = true)
    public List<AdminArticleResponseDTO> findAll(EditorialStatus status) {
        List<Article> articles = status == null
                ? articleRepository.findAllByOrderByUpdatedAtDesc()
                : articleRepository.findAllByStatusOrderByUpdatedAtDesc(status);

        return articles.stream()
                .map(articleAdminMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public AdminArticleResponseDTO findById(Long id) {
        return articleAdminMapper.toResponse(findEntityById(id));
    }

    @Transactional
    public AdminArticleResponseDTO create(AdminArticleUpsertRequestDTO request) {
        Article entity = articleAdminMapper.toEntity(request);
        entity.setSlug(resolveSlug(request.slug(), request.title(), null));
        entity.setFeatured(Boolean.TRUE.equals(request.featured()));
        entity.setReadTimeMinutes(resolveReadTime(request));
        applyEditorialState(entity, request.status(), request.publishedAt());
        return articleAdminMapper.toResponse(articleRepository.save(entity));
    }

    @Transactional
    public AdminArticleResponseDTO update(Long id, AdminArticleUpsertRequestDTO request) {
        Article entity = findEntityById(id);
        articleAdminMapper.updateEntity(request, entity);
        entity.setSlug(resolveSlug(request.slug(), request.title(), id));
        entity.setFeatured(Boolean.TRUE.equals(request.featured()));
        entity.setReadTimeMinutes(resolveReadTime(request));
        applyEditorialState(entity, request.status(), request.publishedAt());
        return articleAdminMapper.toResponse(articleRepository.save(entity));
    }

    @Transactional
    public AdminArticleResponseDTO publish(Long id) {
        Article entity = findEntityById(id);
        entity.setStatus(EditorialStatus.PUBLISHED);
        if (entity.getPublishedAt() == null) {
            entity.setPublishedAt(LocalDateTime.now());
        }
        return articleAdminMapper.toResponse(articleRepository.save(entity));
    }

    @Transactional
    public AdminArticleResponseDTO moveToDraft(Long id) {
        Article entity = findEntityById(id);
        entity.setStatus(EditorialStatus.DRAFT);
        entity.setPublishedAt(null);
        return articleAdminMapper.toResponse(articleRepository.save(entity));
    }

    @Transactional
    public AdminArticleResponseDTO archive(Long id) {
        Article entity = findEntityById(id);
        entity.setStatus(EditorialStatus.ARCHIVED);
        entity.setPublishedAt(null);
        return articleAdminMapper.toResponse(articleRepository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        Article entity = findEntityById(id);
        articleRepository.delete(entity);
    }

    private Article findEntityById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artigo nao encontrado."));
    }

    private int resolveReadTime(AdminArticleUpsertRequestDTO request) {
        if (request.readTimeMinutes() != null && request.readTimeMinutes() > 0) {
            return request.readTimeMinutes();
        }
        return ReadTimeCalculator.estimateMinutes(request.body());
    }

    private String resolveSlug(String requestedSlug, String title, Long currentId) {
        String source = requestedSlug != null && !requestedSlug.isBlank() ? requestedSlug : title;
        String baseSlug = SlugGenerator.slugify(source);

        if (baseSlug.isBlank()) {
            throw new BusinessException("Nao foi possivel gerar um slug valido para o artigo.");
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
                ? articleRepository.existsBySlug(slug)
                : articleRepository.existsBySlugAndIdNot(slug, currentId);
    }

    private void applyEditorialState(Article entity, EditorialStatus requestedStatus, LocalDateTime requestedPublishedAt) {
        EditorialStatus status = requestedStatus != null ? requestedStatus : EditorialStatus.DRAFT;
        entity.setStatus(status);

        if (status == EditorialStatus.PUBLISHED) {
            entity.setPublishedAt(requestedPublishedAt != null ? requestedPublishedAt : LocalDateTime.now());
            return;
        }

        entity.setPublishedAt(null);
    }
}
