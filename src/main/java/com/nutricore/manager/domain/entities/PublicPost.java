package com.nutricore.manager.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_public_posts")
public class PublicPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 140)
    private String title;

    @Column(nullable = false, unique = true, length = 140)
    private String slug;

    @Column(nullable = false, length = 80)
    private String category;

    @Column(nullable = false, length = 500)
    private String excerpt;

    @Column(name = "read_time_minutes", nullable = false)
    private Integer readTimeMinutes;

    @Column(nullable = false, length = 255)
    private String imageUrl;

    @Column(name = "published_at", nullable = false)
    private LocalDate publishedAt;
}
