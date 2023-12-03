package com.wex.exchangetransactions.unit.model;
import com.wex.exchangetransactions.model.PurchaseTransactionModel;
import jakarta.validation.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertThat(violations.iterator().next().getMessage()).isEqualTo("the transaction amount must be greater than zero.");
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
        assertThat(violations.iterator().next().getMessage()).isEqualTo("the description must have size between 0 and 50 characters.");
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
        assertThat(violations.iterator().next().getMessage()).isEqualTo("transaction amount can't be null.");
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
        assertThat(violations.iterator().next().getMessage()).isEqualTo("description can't be null.");
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
        assertThat(violations.iterator().next().getMessage()).isEqualTo("transaction date can't be null.");
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
        assertThat(violations.iterator().next().getMessage()).isEqualTo("transaction timestamp can't be null.");
    }
}
