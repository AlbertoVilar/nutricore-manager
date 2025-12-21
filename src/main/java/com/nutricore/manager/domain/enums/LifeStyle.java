package com.nutricore.manager.domain.enums;

import lombok.Getter;

@Getter
public enum LifeStyle {
    SEDENTARY("Sedentary - Little or no exercise"),
    LIGHTLY_ACTIVE("Lightly Active - Exercise 1-3 days/week"),
    MODERATELY_ACTIVE("Moderately Active - Exercise 3-5 days/week"),
    VERY_ACTIVE("Very Active - Hard exercise 6-7 days/week"),
    EXTREMELY_ACTIVE("Extremely Active - Very hard exercise & physical job");

    private final String description;

    LifeStyle(String description) {
        this.description = description;
    }
}