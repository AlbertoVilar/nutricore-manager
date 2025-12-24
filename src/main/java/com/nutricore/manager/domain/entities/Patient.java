package com.nutricore.manager.domain.entities;

import com.nutricore.manager.domain.enums.patient.Gender;
import com.nutricore.manager.domain.enums.patient.LifeStyle;
import com.nutricore.manager.domain.enums.patient.PatientStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_patients") // Padronizando o prefixo tb_ como na anamnese
@EntityListeners(AuditingEntityListener.class)
@Schema(description = "Entidade que representa o paciente no sistema")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter(AccessLevel.NONE)
    private Set<ClinicalAnamnesis> anamnesisRecords = new HashSet<>();

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Métodos de Negócio (Isso sim pertence ao Domínio!)
    public int calculateAge() {
        return (birthDate == null) ? 0 : java.time.Period.between(birthDate, LocalDate.now()).getYears();
    }

    public void addAnamnesis(ClinicalAnamnesis anamnesis) {
        if (anamnesis == null) return;
        this.anamnesisRecords.add(anamnesis);
        anamnesis.setPatient(this);
    }
}