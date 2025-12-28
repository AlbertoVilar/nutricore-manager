package com.nutricore.manager.domain.enums.goal;

import java.math.BigDecimal;

public enum ActivityLevel {

    SEDENTARY("1.20"),
    LIGHTLY_ACTIVE("1.375"),
    MODERATELY_ACTIVE("1.55"),
    VERY_ACTIVE("1.725"),
    ATHLETE("1.90");

    private final BigDecimal factor;

    ActivityLevel(String factor) {
        this.factor = new BigDecimal(factor);
    }

    public BigDecimal getFactor() {
        return factor;
    }
}