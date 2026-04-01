package com.nutricore.manager.domain.services;

import com.nutricore.manager.api.dto.AdminPostResponseDTO;
import com.nutricore.manager.api.dto.AdminPostUpsertRequestDTO;
import com.nutricore.manager.api.mappers.PostAdminMapper;
import com.nutricore.manager.domain.entities.Post;
import com.nutricore.manager.domain.enums.editorial.EditorialStatus;
import com.nutricore.manager.domain.exceptions.BusinessException;
import com.nutricore.manager.domain.exceptions.ResourceNotFoundException;
import com.nutricore.manager.domain.utils.SlugGenerator;
import com.nutricore.manager.infrastructure.db.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostAdminService {

    private final PostRepository postRepository;
    private final PostAdminMapper postAdminMapper;

    @Transactional(readOnly = true)
    public List<AdminPostResponseDTO> findAll(EditorialStatus status) {
        List<Post> posts = status == null
                ? postRepository.findAllByOrderByUpdatedAtDesc()
                : postRepository.findAllByStatusOrderByUpdatedAtDesc(status);

        return posts.stream()
                .map(postAdminMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public AdminPostResponseDTO findById(Long id) {
        return postAdminMapper.toResponse(findEntityById(id));
    }

    @Transactional
    public AdminPostResponseDTO create(AdminPostUpsertRequestDTO request) {
        Post entity = postAdminMapper.toEntity(request);
        entity.setSlug(resolveSlug(request.slug(), request.title(), null));
        entity.setFeatured(Boolean.TRUE.equals(request.featured()));
        applyEditorialState(entity, request.status(), request.publishedAt());
        return postAdminMapper.toResponse(postRepository.save(entity));
    }

    @Transactional
    public AdminPostResponseDTO update(Long id, AdminPostUpsertRequestDTO request) {
        Post entity = findEntityById(id);
        postAdminMapper.updateEntity(request, entity);
        entity.setSlug(resolveSlug(request.slug(), request.title(), id));
        entity.setFeatured(Boolean.TRUE.equals(request.featured()));
        applyEditorialState(entity, request.status(), request.publishedAt());
        return postAdminMapper.toResponse(postRepository.save(entity));
    }

    @Transactional
    public AdminPostResponseDTO publish(Long id) {
        Post entity = findEntityById(id);
        entity.setStatus(EditorialStatus.PUBLISHED);
        if (entity.getPublishedAt() == null) {
            entity.setPublishedAt(LocalDateTime.now());
        }
        return postAdminMapper.toResponse(postRepository.save(entity));
    }

    @Transactional
    public AdminPostResponseDTO moveToDraft(Long id) {
        Post entity = findEntityById(id);
        entity.setStatus(EditorialStatus.DRAFT);
        entity.setPublishedAt(null);
        return postAdminMapper.toResponse(postRepository.save(entity));
    }

    @Transactional
    public AdminPostResponseDTO archive(Long id) {
        Post entity = findEntityById(id);
        entity.setStatus(EditorialStatus.ARCHIVED);
        entity.setPublishedAt(null);
        return postAdminMapper.toResponse(postRepository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        Post entity = findEntityById(id);
        postRepository.delete(entity);
    }

    private Post findEntityById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post não encontrado."));
    }

    private String resolveSlug(String requestedSlug, String title, Long currentId) {
        String source = requestedSlug != null && !requestedSlug.isBlank() ? requestedSlug : title;
        String baseSlug = SlugGenerator.slugify(source);

        if (baseSlug.isBlank()) {
            throw new BusinessException("Não foi possível gerar um slug válido para o post.");
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
                ? postRepository.existsBySlug(slug)
                : postRepository.existsBySlugAndIdNot(slug, currentId);
    }

    private void applyEditorialState(Post entity, EditorialStatus requestedStatus, LocalDateTime requestedPublishedAt) {
        EditorialStatus status = requestedStatus != null ? requestedStatus : EditorialStatus.DRAFT;
        entity.setStatus(status);

        if (status == EditorialStatus.PUBLISHED) {
            entity.setPublishedAt(requestedPublishedAt != null ? requestedPublishedAt : LocalDateTime.now());
            return;
        }

        entity.setPublishedAt(null);
    }
}
