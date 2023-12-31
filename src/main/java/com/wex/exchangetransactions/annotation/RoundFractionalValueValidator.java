package com.wex.exchangetransactions.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

//Annotation only for double values!
public class RoundFractionalValueValidator implements ConstraintValidator<RoundFractionalValue, BigDecimal> {
    private int fractionDigits;

    @Override
    public void initialize(RoundFractionalValue constraintAnnotation) {
        this.fractionDigits = constraintAnnotation.fractionDigits();
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        String[] parts = String.valueOf(value.doubleValue()).split("\\.");
        return parts[1].length() <= this.fractionDigits;
    }
}
