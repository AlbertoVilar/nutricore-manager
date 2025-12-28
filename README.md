# NutriCore Manager

Sistema de gestão nutricional focado em acompanhamento antropométrico e metas nutricionais personalizadas.

## Modelo de Domínio

Abaixo está o diagrama de classes que representa a estrutura core do sistema:

```mermaid
classDiagram
    direction LR

    %% =========================
    %% AGGREGATE: PATIENT
    %% =========================
    class Patient {
        +UUID id
        +String name
        +LocalDate birthDate
        +Sex sex
        +String email
    }

    %% =========================
    %% AGGREGATE: CLINICAL ANAMNESIS
    %% =========================
    class ClinicalAnamnesis {
        +UUID id
        +Boolean diabetes
        +Boolean hypertension
        +Boolean dyslipidemia
        +String observations
    }

    %% =========================
    %% AGGREGATE: ANTHROPOMETRIC ASSESSMENT
    %% =========================
    class AnthropometricAssessment {
        +UUID id
        +BigDecimal weight
        +BigDecimal height
        +BigDecimal waist
        +BigDecimal hip
        +BigDecimal bmi
        +BigDecimal rcq
        +BigDecimal fatMass
        +BigDecimal leanMass
        +BigDecimal bmr
        +BigDecimal tdee
        +ActivityLevel activityLevel
        +LocalDate assessmentDate
    }

    %% =========================
    %% AGGREGATE: NUTRITION GOAL
    %% =========================
    class NutritionGoal {
        +UUID id
        +GoalType goalType
        +BigDecimal targetCalories
        +BigDecimal proteinGrams
        +BigDecimal carbsGrams
        +BigDecimal fatGrams
        +Boolean active
        +LocalDate createdAt
    }

    %% =========================
    %% ENUMS
    %% =========================
    class ActivityLevel {
        <<enum>>
        SEDENTARY
        LIGHT
        MODERATE
        HIGH
        VERY_HIGH
    }

    class GoalType {
        <<enum>>
        WEIGHT_LOSS
        WEIGHT_GAIN
        MAINTENANCE
    }

    %% =========================
    %% DOMAIN SERVICES
    %% =========================
    class AnthropometricService {
        <<service>>
        +calculateBMI()
        +calculateRCQ()
        +calculateBMR()
        +calculateTDEE()
    }

    class NutritionGoalService {
        <<service>>
        +createGoal()
        +recalculateGoal()
        +deactivatePreviousGoal()
    }

    %% =========================
    %% STRATEGY PATTERN
    %% =========================
    class CaloricStrategy {
        <<interface>>
        +calculateCalories(tdee)
    }

    class WeightLossStrategy {
        +calculateCalories(tdee)
    }

    class WeightGainStrategy {
        +calculateCalories(tdee)
    }

    class MaintenanceStrategy {
        +calculateCalories(tdee)
    }

    %% =========================
    %% RELATIONSHIPS
    %% =========================
    Patient "1" --> "1" ClinicalAnamnesis
    Patient "1" --> "*" AnthropometricAssessment
    Patient "1" --> "*" NutritionGoal

    NutritionGoalService --> AnthropometricAssessment
    NutritionGoalService --> CaloricStrategy

    CaloricStrategy <|.. WeightLossStrategy
    CaloricStrategy <|.. WeightGainStrategy
    CaloricStrategy <|.. MaintenanceStrategy
```
