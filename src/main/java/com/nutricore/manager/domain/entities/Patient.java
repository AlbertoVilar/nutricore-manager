package com.nutricore.manager.domain.entities;

import com.nutricore.manager.domain.enums.Gender;
import com.nutricore.manager.domain.enums.LifeStyle;
import com.nutricore.manager.domain.enums.PatientStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "patients")
@EntityListeners(AuditingEntityListener.class) // Necessário para ativar as anotações de auditoria nesta classe
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Alterado para Long (Wrapper) para melhor suporte JPA

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Gender gender;

    @Column(length = 100)
    private String occupation;

    @Enumerated(EnumType.STRING)
    @Column(name = "life_style", nullable = false, length = 30)
    private LifeStyle lifeStyle;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PatientStatus status;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Helper method to ensure new patients start as active
    @PrePersist
    public void ensureActiveStatus() {
        if (this.status == null) {
            this.status = PatientStatus.ACTIVE;
        }
    }
}