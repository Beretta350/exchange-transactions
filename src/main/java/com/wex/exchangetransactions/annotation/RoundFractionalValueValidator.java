package com.wex.exchangetransactions.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

//Annotation only for double values!
public class RoundFractionalValueValidator implements ConstraintValidator<RoundFractionalValue, Double> {
    private int fractionDigits;

    @Override
    public void initialize(RoundFractionalValue constraintAnnotation) {
        this.fractionDigits = constraintAnnotation.fractionDigits();
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        String[] parts = String.valueOf(value).split("\\.");
        return parts[1].length() <= this.fractionDigits;
    }
}
