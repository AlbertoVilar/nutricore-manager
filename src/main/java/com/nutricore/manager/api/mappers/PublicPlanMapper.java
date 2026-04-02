package com.nutricore.manager.api.mappers;

import com.nutricore.manager.api.dto.PublicPlanAdminRequestDTO;
import com.nutricore.manager.api.dto.PublicPlanAdminResponseDTO;
import com.nutricore.manager.api.dto.PublicPlanResponseDTO;
import com.nutricore.manager.domain.entities.PublicPlan;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PublicPlanMapper {

    public PublicPlanResponseDTO toResponse(PublicPlan entity) {
        return new PublicPlanResponseDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getSubtitle(),
                entity.getPriceLabel(),
                entity.getSummary(),
                splitLines(entity.getFeatures()),
                entity.getFeatured(),
                entity.getCtaLabel(),
                entity.getCtaUrl()
        );
    }

    public PublicPlanAdminResponseDTO toAdminResponse(PublicPlan entity) {
        return new PublicPlanAdminResponseDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getSubtitle(),
                entity.getPriceLabel(),
                entity.getSummary(),
                splitLines(entity.getFeatures()),
                entity.getFeatured(),
                entity.getActive(),
                entity.getCtaLabel(),
                entity.getCtaUrl(),
                entity.getDisplayOrder(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public PublicPlan toEntity(PublicPlanAdminRequestDTO request) {
        PublicPlan entity = new PublicPlan();
        updateEntity(entity, request);
        return entity;
    }

    public void updateEntity(PublicPlan entity, PublicPlanAdminRequestDTO request) {
        entity.setTitle(request.title().trim());
        entity.setSubtitle(request.subtitle().trim());
        entity.setPriceLabel(request.priceLabel().trim());
        entity.setSummary(request.summary().trim());
        entity.setFeatures(joinLines(request.features()));
        entity.setFeatured(request.featured());
        entity.setActive(request.active());
        entity.setCtaLabel(request.ctaLabel().trim());
        entity.setCtaUrl(request.ctaUrl().trim());
        entity.setDisplayOrder(request.displayOrder());
    }

    private List<String> splitLines(String value) {
        if (value == null || value.isBlank()) {
            return List.of();
        }

        return Arrays.stream(value.split("\\R"))
                .map(String::trim)
                .filter(line -> !line.isBlank())
                .toList();
    }

    private String joinLines(List<String> value) {
        return value.stream()
                .map(String::trim)
                .filter(line -> !line.isBlank())
                .reduce((left, right) -> left + System.lineSeparator() + right)
                .orElse("");
    }
}
