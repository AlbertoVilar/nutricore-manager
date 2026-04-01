package com.nutricore.manager.api.dto;

public record PublicProfileResponseDTO(
        Long id,
        String fullName,
        String professionalTitle,
        String heroBadge,
        String heroTitle,
        String heroDescription,
        String aboutTitle,
        String aboutDescription,
        String primaryCtaLabel,
        String primaryCtaUrl,
        String secondaryCtaLabel,
        String secondaryCtaUrl,
        String contactEmail,
        String contactPhone,
        String instagramHandle,
        String city,
        String heroImageUrl
) {
}
