# NutriCore Manager

Sistema de gestão nutricional focado em acompanhamento antropométrico e metas nutricionais personalizadas.

## Modelo de Domínio

Abaixo está o diagrama de classes que representa a estrutura core do sistema:

```mermaid
classDiagram
    %% =====================
    %% AGGREGADO RAIZ
    %% =====================
    class Patient {
        -Long id
        -String name
        -String email
        -String phone
        -LocalDate birthDate
        -Gender gender
        -LifeStyle lifeStyle
        -PatientStatus status
        +calculateAge() int
    }

    %% =====================
    %% HISTÓRICO CLÍNICO
    %% =====================
    class ClinicalAnamnesis {
        -Long id
        -LocalDate date
        -SleepQuality sleepQuality
        -BowelFunction bowelFunction
        -Double waterIntakeGoal
        -String clinicalHistory
        -String familyHistory
        +isRecent() boolean
    }

    %% =====================
    %% AVALIAÇÃO ANTROPOMÉTRICA
    %% =====================
    class AnthropometricAssessment {
        -Long id
        -LocalDate assessmentDate
        -BigDecimal weight
        -BigDecimal height
        -BigDecimal bmi
        -BigDecimal bodyFatPercentage
        -BigDecimal fatMassKg
        -BigDecimal leanMassKg
        -BigDecimal leanMassPercentage
        -BigDecimal basalMetabolicRate
        -BigDecimal totalEnergyExpenditure
        -ActivityLevel activityLevel
        -BigDecimal waist
        -BigDecimal hip
        -BigDecimal waistHipRatio
        +performCalculations()
    }

    %% =====================
    %% OBJETIVO NUTRICIONAL
    %% =====================
    class NutritionGoal {
        -Long id
        -GoalType goalType
        -GoalStatus status
        -BigDecimal targetWeight
        -BigDecimal targetBodyFatPercentage
        -BigDecimal targetLeanMass
        -BigDecimal targetCalories
        -LocalDate startDate
        -LocalDate expectedEndDate
        +activate()
        +complete()
        +cancel()
        +isOverdue() boolean
        +getOverdueDays() long
    }

    %% =====================
    %% PROFISSIONAL
    %% =====================
    class Nutritionist {
        -Long id
        -String name
        -String email
        -String crn
        -String specialty
    }

    %% =====================
    %% SERVIÇOS
    %% =====================
    class NutritionGoalService {
        +create()
        +update()
        +findById()
        +delete()
        -calculateEnergyPlan()
    }

    class AnthropometricService {
        +create()
        +update()
        +findAllByPatientId()
        +delete()
    }

    %% =====================
    %% UTILITÁRIOS
    %% =====================
    class EnergyCalculator {
        +calculateBmr()
        +calculateTdee()
    }

    %% =====================
    %% ENUMS
    %% =====================
    class Gender {
        <<enumeration>>
        MALE
        FEMALE
    }

    class GoalType {
        <<enumeration>>
        WEIGHT_LOSS
        MAINTENANCE
        HYPERTROPHY
    }

    class GoalStatus {
        <<enumeration>>
        PLANNED
        ACTIVE
        COMPLETED
        CANCELLED
    }

    class ActivityLevel {
        <<enumeration>>
        SEDENTARY
        MODERATELY_ACTIVE
        VERY_ACTIVE
    }

    class SleepQuality {
        <<enumeration>>
    }

    class BowelFunction {
        <<enumeration>>
    }

    class LifeStyle {
        <<enumeration>>
    }

    class PatientStatus {
        <<enumeration>>
    }

    %% =====================
    %% RELACIONAMENTOS
    %% =====================
    Patient "1" o-- "*" ClinicalAnamnesis : possui
    Patient "1" o-- "*" AnthropometricAssessment : possui
    Patient "1" o-- "*" NutritionGoal : possui
    Nutritionist "1" o-- "*" Patient : atende

    Patient --> Gender : tem
    Patient --> LifeStyle : tem
    Patient --> PatientStatus : tem
    
    ClinicalAnamnesis --> SleepQuality : registra
    ClinicalAnamnesis --> BowelFunction : registra
    
    AnthropometricAssessment --> ActivityLevel : tem
    
    NutritionGoal --> GoalType : tem
    NutritionGoal --> GoalStatus : tem

    %% Serviços e suas dependências
    NutritionGoalService ..> Patient : usa
    NutritionGoalService ..> AnthropometricAssessment : usa
    NutritionGoalService ..> NutritionGoal : gerencia
    NutritionGoalService ..> EnergyCalculator : usa

    AnthropometricService ..> Patient : usa
    AnthropometricService ..> AnthropometricAssessment : gerencia
    AnthropometricService ..> EnergyCalculator : usa
```
