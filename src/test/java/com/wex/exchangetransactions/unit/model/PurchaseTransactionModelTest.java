package com.wex.exchangetransactions.unit.model;

import com.wex.exchangetransactions.model.PurchaseTransactionModel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static com.wex.exchangetransactions.exception.error.ValidationErrorMessages.*;
import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseTransactionModelTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    void purchaseTransactionModelValidationSuccessTest() {
        // Arrange
        PurchaseTransactionModel transaction = new PurchaseTransactionModel(
                UUID.randomUUID(),
                100.0,
                "Some description",
                LocalDate.now(),
                LocalDateTime.now()
        );
        Set<?> violations = validator.validate(transaction);
        assertThat(violations).isEmpty();
    }

    @Test
    void purchaseTransactionModelAmountLessThanZeroFailTest() {
        PurchaseTransactionModel transaction = new PurchaseTransactionModel(
                UUID.randomUUID(),
                -10.0,
                "Some description",
                LocalDate.now(),
                LocalDateTime.now()
        );

        Set<ConstraintViolation<PurchaseTransactionModel>> violations = validator.validate(transaction);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo(TRANSACTION_AMOUNT_MIN_MESSAGE);
    }

    @Test
    void purchaseTransactionModelDescriptionTooLongFailTest() {
        // Arrange
        String longDescription = "Very very very very long description that exceeds the maximum length of 50 characters.";
        PurchaseTransactionModel transaction = new PurchaseTransactionModel(
                UUID.randomUUID(),
                100.0,
                longDescription,
                LocalDate.now(),
                LocalDateTime.now()
        );

        Set<ConstraintViolation<PurchaseTransactionModel>> violations = validator.validate(transaction);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo(DESCRIPTION_SIZE_MESSAGE);
    }

    @Test
    void purchaseTransactionModelNullAmountFailTest() {
        PurchaseTransactionModel transaction = new PurchaseTransactionModel(
                UUID.randomUUID(),
                null,
                "Some description",
                LocalDate.now(),
                LocalDateTime.now()
        );
        Set<ConstraintViolation<PurchaseTransactionModel>> violations = validator.validate(transaction);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo(TRANSACTION_AMOUNT_NOT_NULL_MESSAGE);
    }

    @Test
    void purchaseTransactionModelNullDescriptionFailTest() {
        PurchaseTransactionModel transaction = new PurchaseTransactionModel(
                UUID.randomUUID(),
                20.00,
                null,
                LocalDate.now(),
                LocalDateTime.now()
        );
        Set<ConstraintViolation<PurchaseTransactionModel>> violations = validator.validate(transaction);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo(DESCRIPTION_NOT_NULL_MESSAGE);
    }

    @Test
    void purchaseTransactionModelNullTransactionDateFailTest() {
        PurchaseTransactionModel transaction = new PurchaseTransactionModel(
                UUID.randomUUID(),
                20.00,
                "Some description",
                null,
                LocalDateTime.now()
        );
        Set<ConstraintViolation<PurchaseTransactionModel>> violations = validator.validate(transaction);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo(TRANSACTION_DATE_NOT_NULL_MESSAGE);
    }

    @Test
    void purchaseTransactionModelNullTransactionTimestampFailTest() {
        PurchaseTransactionModel transaction = new PurchaseTransactionModel(
                UUID.randomUUID(),
                20.00,
                "Some description",
                LocalDate.now(),
                null
        );
        Set<ConstraintViolation<PurchaseTransactionModel>> violations = validator.validate(transaction);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo(TRANSACTION_TIMESTAMP_NOT_NULL_MESSAGE);
    }

    @Test
    void purchaseTransactionModelFractionalSizeFailTest() {
        PurchaseTransactionModel transaction = new PurchaseTransactionModel(
                UUID.randomUUID(),
                20.001,
                "Some description",
                LocalDate.now(),
                LocalDateTime.now()
        );
        Set<ConstraintViolation<PurchaseTransactionModel>> violations = validator.validate(transaction);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo(TRANSACTION_AMOUNT_ROUNDED_MESSAGE);
    }
}
