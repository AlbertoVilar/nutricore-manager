package com.nutricore.manager.domain.utils;

import com.nutricore.manager.domain.enums.antropometrics.BmiClassification;
import com.nutricore.manager.domain.enums.antropometrics.WaistHipRisk;

import java.math.BigDecimal;

public final class NutriCalculators {

    private NutriCalculators() {
        throw new UnsupportedOperationException("Esta é uma classe utilitária e não pode ser instanciada");
    }

    /* ==========================
       CONSTANTES – BMI
       ========================== */

    private static final BigDecimal BMI_UNDERWEIGHT = BigDecimal.valueOf(18.5);
    private static final BigDecimal BMI_NORMAL = BigDecimal.valueOf(25);
    private static final BigDecimal BMI_OVERWEIGHT = BigDecimal.valueOf(30);
    private static final BigDecimal BMI_OBESITY_I = BigDecimal.valueOf(35);
    private static final BigDecimal BMI_OBESITY_II = BigDecimal.valueOf(40);

    /* ==========================
       CONSTANTES – RCQ
       ========================== */

    private static final BigDecimal RCQ_LOW_RISK = BigDecimal.valueOf(0.85);
    private static final BigDecimal RCQ_MODERATE_RISK = BigDecimal.valueOf(0.95);

    /* ==========================
       CLASSIFICAÇÃO BMI
       ========================== */

    public static BmiClassification classifyBmi(BigDecimal bmi) {

        if (bmi == null) {
            return null;
        }

        if (bmi.compareTo(BMI_UNDERWEIGHT) < 0) {
            return BmiClassification.UNDERWEIGHT;
        }

        if (bmi.compareTo(BMI_NORMAL) < 0) {
            return BmiClassification.NORMAL_WEIGHT;
        }

        if (bmi.compareTo(BMI_OVERWEIGHT) < 0) {
            return BmiClassification.OVERWEIGHT;
        }

        if (bmi.compareTo(BMI_OBESITY_I) < 0) {
            return BmiClassification.OBESITY_GRADE_I;
        }

        if (bmi.compareTo(BMI_OBESITY_II) < 0) {
            return BmiClassification.OBESITY_GRADE_II;
        }

        return BmiClassification.OBESITY_GRADE_III;
    }

    /* ==========================
       CLASSIFICAÇÃO RCQ
       ========================== */

    public static WaistHipRisk classifyWaistHip(BigDecimal rcq) {

        if (rcq == null) {
            return null;
        }

        if (rcq.compareTo(RCQ_LOW_RISK) < 0) {
            return WaistHipRisk.LOW;
        }

        if (rcq.compareTo(RCQ_MODERATE_RISK) < 0) {
            return WaistHipRisk.MODERATE;
        }

        return WaistHipRisk.HIGH;
    }
}
