package com.nutricore.manager.api.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nutricore.manager.api.dto.PublicProfileAdminRequestDTO;
import com.nutricore.manager.api.dto.PublicProfileAdminResponseDTO;
import com.nutricore.manager.api.dto.PublicProfileResponseDTO;
import com.nutricore.manager.api.dto.PublicServicePillarItemDTO;
import com.nutricore.manager.api.dto.PublicSiteMetricItemDTO;
import com.nutricore.manager.api.dto.PublicTestimonialItemDTO;
import com.nutricore.manager.domain.entities.PublicProfile;
import com.nutricore.manager.domain.exceptions.BusinessException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PublicProfileMapper {

    private final ObjectMapper objectMapper;

    public PublicProfileMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public PublicProfileResponseDTO toResponse(PublicProfile entity) {
        return new PublicProfileResponseDTO(
                entity.getId(),
                entity.getFullName(),
                entity.getProfessionalTitle(),
                entity.getProfessionalSubtitle(),
                entity.getBiographySummary(),
                entity.getHeroBadge(),
                entity.getHeroTitle(),
                entity.getHeroDescription(),
                entity.getHeroCardTitle(),
                entity.getHeroCardDescription(),
                entity.getAboutTitle(),
                entity.getAboutDescription(),
                entity.getAboutImageUrl(),
                entity.getPrimaryCtaLabel(),
                entity.getPrimaryCtaUrl(),
                entity.getSecondaryCtaLabel(),
                entity.getSecondaryCtaUrl(),
                entity.getContactEmail(),
                entity.getContactPhone(),
                entity.getWhatsappNumber(),
                entity.getInstagramHandle(),
                entity.getLinkedinUrl(),
                entity.getYoutubeUrl(),
                entity.getCity(),
                entity.getOfficeAddress(),
                entity.getHeroImageUrl(),
                entity.getFooterDescription(),
                entity.getHowItWorksTitle(),
                entity.getHowItWorksDescription(),
                entity.getApproachTitle(),
                entity.getApproachDescription(),
                entity.getPlansTitle(),
                entity.getPlansDescription(),
                entity.getContactTitle(),
                entity.getContactDescription(),
                entity.getFinalCtaTitle(),
                entity.getFinalCtaDescription(),
                readMetrics(entity.getSiteMetricsJson()),
                readPillars(entity.getServicePillarsJson()),
                readTestimonials(entity.getTestimonialsJson())
        );
    }

    public PublicProfileAdminResponseDTO toAdminResponse(PublicProfile entity) {
        return new PublicProfileAdminResponseDTO(
                entity.getId(),
                entity.getFullName(),
                entity.getProfessionalTitle(),
                entity.getProfessionalSubtitle(),
                entity.getBiographySummary(),
                entity.getHeroBadge(),
                entity.getHeroTitle(),
                entity.getHeroDescription(),
                entity.getHeroCardTitle(),
                entity.getHeroCardDescription(),
                entity.getAboutTitle(),
                entity.getAboutDescription(),
                entity.getHeroImageUrl(),
                entity.getAboutImageUrl(),
                entity.getPrimaryCtaLabel(),
                entity.getPrimaryCtaUrl(),
                entity.getSecondaryCtaLabel(),
                entity.getSecondaryCtaUrl(),
                entity.getContactEmail(),
                entity.getContactPhone(),
                entity.getWhatsappNumber(),
                entity.getInstagramHandle(),
                entity.getLinkedinUrl(),
                entity.getYoutubeUrl(),
                entity.getCity(),
                entity.getOfficeAddress(),
                entity.getFooterDescription(),
                entity.getHowItWorksTitle(),
                entity.getHowItWorksDescription(),
                entity.getApproachTitle(),
                entity.getApproachDescription(),
                entity.getPlansTitle(),
                entity.getPlansDescription(),
                entity.getContactTitle(),
                entity.getContactDescription(),
                entity.getFinalCtaTitle(),
                entity.getFinalCtaDescription(),
                readMetrics(entity.getSiteMetricsJson()),
                readPillars(entity.getServicePillarsJson()),
                readTestimonials(entity.getTestimonialsJson()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public void updateEntity(PublicProfile entity, PublicProfileAdminRequestDTO request) {
        entity.setFullName(request.fullName().trim());
        entity.setProfessionalTitle(request.professionalTitle().trim());
        entity.setProfessionalSubtitle(request.professionalSubtitle().trim());
        entity.setBiographySummary(request.biographySummary().trim());
        entity.setHeroBadge(request.heroBadge().trim());
        entity.setHeroTitle(request.heroTitle().trim());
        entity.setHeroDescription(request.heroDescription().trim());
        entity.setHeroCardTitle(request.heroCardTitle().trim());
        entity.setHeroCardDescription(request.heroCardDescription().trim());
        entity.setAboutTitle(request.aboutTitle().trim());
        entity.setAboutDescription(request.aboutDescription().trim());
        entity.setHeroImageUrl(request.heroImageUrl().trim());
        entity.setAboutImageUrl(request.aboutImageUrl().trim());
        entity.setPrimaryCtaLabel(request.primaryCtaLabel().trim());
        entity.setPrimaryCtaUrl(request.primaryCtaUrl().trim());
        entity.setSecondaryCtaLabel(request.secondaryCtaLabel().trim());
        entity.setSecondaryCtaUrl(request.secondaryCtaUrl().trim());
        entity.setContactEmail(request.contactEmail().trim().toLowerCase());
        entity.setContactPhone(request.contactPhone().trim());
        entity.setWhatsappNumber(request.whatsappNumber().trim());
        entity.setInstagramHandle(request.instagramHandle().trim());
        entity.setLinkedinUrl(trimToNull(request.linkedinUrl()));
        entity.setYoutubeUrl(trimToNull(request.youtubeUrl()));
        entity.setCity(request.city().trim());
        entity.setOfficeAddress(request.officeAddress().trim());
        entity.setFooterDescription(request.footerDescription().trim());
        entity.setHowItWorksTitle(request.howItWorksTitle().trim());
        entity.setHowItWorksDescription(request.howItWorksDescription().trim());
        entity.setApproachTitle(request.approachTitle().trim());
        entity.setApproachDescription(request.approachDescription().trim());
        entity.setPlansTitle(request.plansTitle().trim());
        entity.setPlansDescription(request.plansDescription().trim());
        entity.setContactTitle(request.contactTitle().trim());
        entity.setContactDescription(request.contactDescription().trim());
        entity.setFinalCtaTitle(request.finalCtaTitle().trim());
        entity.setFinalCtaDescription(request.finalCtaDescription().trim());
        entity.setSiteMetricsJson(writeValue(request.siteMetrics()));
        entity.setServicePillarsJson(writeValue(request.servicePillars()));
        entity.setTestimonialsJson(writeValue(request.testimonials()));
    }

    private List<PublicSiteMetricItemDTO> readMetrics(String rawValue) {
        return readList(rawValue, new TypeReference<>() {});
    }

    private List<PublicServicePillarItemDTO> readPillars(String rawValue) {
        return readList(rawValue, new TypeReference<>() {});
    }

    private List<PublicTestimonialItemDTO> readTestimonials(String rawValue) {
        return readList(rawValue, new TypeReference<>() {});
    }

    private <T> List<T> readList(String rawValue, TypeReference<List<T>> typeReference) {
        if (rawValue == null || rawValue.isBlank()) {
            return List.of();
        }

        try {
            return objectMapper.readValue(rawValue, typeReference);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Não foi possível ler a configuração pública estruturada.", ex);
        }
    }

    private String writeValue(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException ex) {
            throw new BusinessException("Não foi possível salvar a configuração pública estruturada.");
        }
    }

    private String trimToNull(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
