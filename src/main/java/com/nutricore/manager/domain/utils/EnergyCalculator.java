package com.nutricore.manager.domain.utils;

import com.nutricore.manager.domain.enums.goal.ActivityLevel;
import com.nutricore.manager.domain.enums.patient.Gender;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class EnergyCalculator {

    private static final int SCALE = 2;

    private EnergyCalculator() {
    }

    public static BigDecimal calculateBmr(
            BigDecimal weight,
            BigDecimal height,
            int age,
            Gender gender
    ) {

        BigDecimal weightFactor = new BigDecimal("10");
        BigDecimal heightFactor = new BigDecimal("6.25");
        BigDecimal ageFactor = new BigDecimal("5");

        BigDecimal bmr = weight.multiply(weightFactor)
                .add(height.multiply(heightFactor))
                .subtract(ageFactor.multiply(BigDecimal.valueOf(age)));

        if (gender == Gender.MALE) {
            bmr = bmr.add(new BigDecimal("5"));
        } else {
            bmr = bmr.subtract(new BigDecimal("161"));
        }

        return bmr.setScale(SCALE, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateTdee(
            BigDecimal bmr,
            ActivityLevel activityLevel
    ) {
        return bmr
                .multiply(activityLevel.getFactor())
                .setScale(SCALE, RoundingMode.HALF_UP);
    }
}
