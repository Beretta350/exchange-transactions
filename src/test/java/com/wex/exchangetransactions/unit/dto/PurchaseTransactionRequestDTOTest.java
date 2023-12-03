package com.wex.exchangetransactions.unit.dto;

import com.wex.exchangetransactions.dto.PurchaseTransactionRequestDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static com.wex.exchangetransactions.exception.error.ValidationErrorMessages.*;
import static org.assertj.core.api.Assertions.assertThat;

public class PurchaseTransactionRequestDTOTest {
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    void PurchaseTransactionRequestDTOValidationSuccessTest() {
        // Arrange
        PurchaseTransactionRequestDTO transaction = new PurchaseTransactionRequestDTO(
                100.0,
                "Some description",
                LocalDate.now()
        );
        Set<?> violations = validator.validate(transaction);
        assertThat(violations).isEmpty();
    }

    @Test
    void PurchaseTransactionRequestDTOAmountLessThanZeroFailTest() {
        PurchaseTransactionRequestDTO transaction = new PurchaseTransactionRequestDTO(
                -100.0,
                "Some description",
                LocalDate.now()
        );

        Set<ConstraintViolation<PurchaseTransactionRequestDTO>> violations = validator.validate(transaction);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo(TRANSACTION_AMOUNT_MIN_MESSAGE);
    }

    @Test
    void PurchaseTransactionRequestDTODescriptionTooLongFailTest() {
        // Arrange
        String longDescription = "Very very very very long description that exceeds the maximum length of 50 characters.";
        PurchaseTransactionRequestDTO transaction = new PurchaseTransactionRequestDTO(
                100.0,
                longDescription,
                LocalDate.now()
        );

        Set<ConstraintViolation<PurchaseTransactionRequestDTO>> violations = validator.validate(transaction);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo(DESCRIPTION_SIZE_MESSAGE);
    }

    @Test
    void PurchaseTransactionRequestDTONullAmountFailTest() {
        PurchaseTransactionRequestDTO transaction = new PurchaseTransactionRequestDTO(
                null,
                "Some description",
                LocalDate.now()
        );
        Set<ConstraintViolation<PurchaseTransactionRequestDTO>> violations = validator.validate(transaction);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo(TRANSACTION_AMOUNT_NOT_NULL_MESSAGE);
    }

    @Test
    void PurchaseTransactionRequestDTONullDescriptionFailTest() {
        PurchaseTransactionRequestDTO transaction = new PurchaseTransactionRequestDTO(
                100.0,
                null,
                LocalDate.now()
        );
        Set<ConstraintViolation<PurchaseTransactionRequestDTO>> violations = validator.validate(transaction);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo(DESCRIPTION_NOT_NULL_MESSAGE);
    }

    @Test
    void PurchaseTransactionRequestDTONullTransactionDateFailTest() {
        PurchaseTransactionRequestDTO transaction = new PurchaseTransactionRequestDTO(
                100.0,
                "Some description",
                null
        );
        Set<ConstraintViolation<PurchaseTransactionRequestDTO>> violations = validator.validate(transaction);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo(TRANSACTION_DATE_NOT_NULL_MESSAGE);
    }

    @Test
    void PurchaseTransactionRequestDTOTransactionAmountFailTest() {
        PurchaseTransactionRequestDTO transaction = new PurchaseTransactionRequestDTO(
                100.001,
                "Some description",
                LocalDate.now()
        );
        Set<ConstraintViolation<PurchaseTransactionRequestDTO>> violations = validator.validate(transaction);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo(TRANSACTION_AMOUNT_ROUNDED_MESSAGE);
    }
}
