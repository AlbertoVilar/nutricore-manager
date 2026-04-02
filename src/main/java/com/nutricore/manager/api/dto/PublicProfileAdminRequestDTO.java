package com.nutricore.manager.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record PublicProfileAdminRequestDTO(
        @NotBlank @Size(max = 120) String fullName,
        @NotBlank @Size(max = 120) String professionalTitle,
        @NotBlank @Size(max = 160) String professionalSubtitle,
        @NotBlank @Size(max = 500) String biographySummary,
        @NotBlank @Size(max = 180) String heroBadge,
        @NotBlank @Size(max = 180) String heroTitle,
        @NotBlank @Size(max = 500) String heroDescription,
        @NotBlank @Size(max = 120) String heroCardTitle,
        @NotBlank @Size(max = 500) String heroCardDescription,
        @NotBlank @Size(max = 120) String aboutTitle,
        @NotBlank @Size(max = 1500) String aboutDescription,
        @NotBlank @Size(max = 255) String heroImageUrl,
        @NotBlank @Size(max = 255) String aboutImageUrl,
        @NotBlank @Size(max = 80) String primaryCtaLabel,
        @NotBlank @Size(max = 255) String primaryCtaUrl,
        @NotBlank @Size(max = 80) String secondaryCtaLabel,
        @NotBlank @Size(max = 255) String secondaryCtaUrl,
        @NotBlank @Email @Size(max = 150) String contactEmail,
        @NotBlank @Size(max = 20) String contactPhone,
        @NotBlank @Size(max = 20) String whatsappNumber,
        @NotBlank @Size(max = 80) String instagramHandle,
        @Size(max = 255) String linkedinUrl,
        @Size(max = 255) String youtubeUrl,
        @NotBlank @Size(max = 80) String city,
        @NotBlank @Size(max = 255) String footerDescription,
        @NotBlank @Size(max = 180) String howItWorksTitle,
        @NotBlank @Size(max = 1200) String howItWorksDescription,
        @NotBlank @Size(max = 180) String approachTitle,
        @NotBlank @Size(max = 1200) String approachDescription,
        @NotBlank @Size(max = 180) String plansTitle,
        @NotBlank @Size(max = 1200) String plansDescription,
        @NotBlank @Size(max = 180) String contactTitle,
        @NotBlank @Size(max = 1200) String contactDescription,
        @NotBlank @Size(max = 180) String finalCtaTitle,
        @NotBlank @Size(max = 1200) String finalCtaDescription,
        @Valid @NotEmpty List<PublicSiteMetricItemDTO> siteMetrics,
        @Valid @NotEmpty List<PublicServicePillarItemDTO> servicePillars,
        @Valid @NotEmpty List<PublicTestimonialItemDTO> testimonials
) {
}
