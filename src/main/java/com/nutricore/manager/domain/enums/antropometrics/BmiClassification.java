package com.nutricore.manager.domain.enums.antropometrics;

public enum BmiClassification {
    UNDERWEIGHT("Abaixo do peso"),
    NORMAL_WEIGHT("Peso normal"),
    OVERWEIGHT("Sobrepeso"),
    OBESITY_GRADE_I("Obesidade Grau I"),
    OBESITY_GRADE_II("Obesidade Grau II"),
    OBESITY_GRADE_III("Obesidade Grau III");

    private final String description;

    BmiClassification(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}