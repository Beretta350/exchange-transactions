package com.wex.exchangetransactions.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RoundFractionalValueValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RoundFractionalValue {
    String message() default "Number is not correctly rounded";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int fractionDigits();
}
