package com.nutricore.manager.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_public_profiles")
@EntityListeners(AuditingEntityListener.class)
public class PublicProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String fullName;

    @Column(nullable = false, length = 120)
    private String professionalTitle;

    @Column(nullable = false, length = 160)
    private String professionalSubtitle;

    @Column(nullable = false, length = 500)
    private String biographySummary;

    @Column(nullable = false, length = 180)
    private String heroBadge;

    @Column(nullable = false, length = 180)
    private String heroTitle;

    @Column(nullable = false, length = 500)
    private String heroDescription;

    @Column(nullable = false, length = 120)
    private String aboutTitle;

    @Column(nullable = false, length = 1500)
    private String aboutDescription;

    @Column(nullable = false, length = 255)
    private String aboutImageUrl;

    @Column(nullable = false, length = 80)
    private String primaryCtaLabel;

    @Column(nullable = false, length = 255)
    private String primaryCtaUrl;

    @Column(nullable = false, length = 80)
    private String secondaryCtaLabel;

    @Column(nullable = false, length = 255)
    private String secondaryCtaUrl;

    @Column(nullable = false, length = 150)
    private String contactEmail;

    @Column(nullable = false, length = 20)
    private String contactPhone;

    @Column(nullable = false, length = 20)
    private String whatsappNumber;

    @Column(nullable = false, length = 80)
    private String instagramHandle;

    @Column(length = 255)
    private String linkedinUrl;

    @Column(length = 255)
    private String youtubeUrl;

    @Column(nullable = false, length = 80)
    private String city;

    @Column(nullable = false, length = 255)
    private String officeAddress;

    @Column(nullable = false, length = 255)
    private String heroImageUrl;

    @Column(nullable = false, length = 120)
    private String heroCardTitle;

    @Column(nullable = false, length = 500)
    private String heroCardDescription;

    @Column(nullable = false, length = 255)
    private String footerDescription;

    @Column(nullable = false, length = 180)
    private String howItWorksTitle;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String howItWorksDescription;

    @Column(nullable = false, length = 180)
    private String approachTitle;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String approachDescription;

    @Column(nullable = false, length = 180)
    private String plansTitle;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String plansDescription;

    @Column(nullable = false, length = 180)
    private String contactTitle;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contactDescription;

    @Column(nullable = false, length = 180)
    private String finalCtaTitle;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String finalCtaDescription;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String siteMetricsJson;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String servicePillarsJson;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String testimonialsJson;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
