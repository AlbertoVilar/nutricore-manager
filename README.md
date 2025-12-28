# NutriCore Manager

Sistema de gestão nutricional focado em acompanhamento antropométrico e metas nutricionais personalizadas.

## Modelo de Domínio

Abaixo está o diagrama de classes que representa a estrutura core do sistema:

```mermaid
classDiagram
    direction LR

    %% =========================
    %% AGGREGATE ROOT
    %% =========================
    class Patient {
        +UUID id
        +String name
        +LocalDate birthDate
        +Sex sex
        +String email
    }

    %% =========================
    %% CLINICAL DATA
    %% =========================
    class ClinicalAnamnesis {
        +UUID id
        +Boolean diabetes
        +Boolean hypertension
        +Boolean dyslipidemia
        +String observations
    }

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
    %% NUTRITION GOAL
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
    %% MEAL PLANNING (FUTURE)
    %% =========================
    class MealPlan {
        +UUID id
        +BigDecimal totalCalories
        +Boolean active
        +LocalDate createdAt
    }

    class Meal {
        +UUID id
        +MealType type
        +BigDecimal totalCalories
    }

    class MealItem {
        +UUID id
        +BigDecimal quantity
        +BigDecimal calories
    }

    class Food {
        +UUID id
        +String name
        +BigDecimal caloriesPer100g
        +BigDecimal proteinPer100g
        +BigDecimal carbsPer100g
        +BigDecimal fatPer100g
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

    class MealType {
        <<enum>>
        BREAKFAST
        LUNCH
        SNACK
        DINNER
        SUPPER
    }

    %% =========================
    %% RELATIONSHIPS
    %% =========================
    Patient "1" --> "1" ClinicalAnamnesis
    Patient "1" --> "*" AnthropometricAssessment
    Patient "1" --> "*" NutritionGoal
    Patient "1" --> "*" MealPlan

    NutritionGoal "1" --> "1" MealPlan

    MealPlan "1" --> "*" Meal
    Meal "1" --> "*" MealItem
    MealItem "*" --> "1" Food
```
