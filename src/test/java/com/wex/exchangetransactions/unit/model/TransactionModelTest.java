package com.wex.exchangetransactions.unit.model;

import com.wex.exchangetransactions.model.TransactionModel;
import com.wex.exchangetransactions.model.TransactionRetrieveHistoryModel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.wex.exchangetransactions.exception.error.ValidationErrorMessages.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionModelTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    void purchaseTransactionModelValidationSuccessTest() {
        // Arrange
        TransactionModel transaction = new TransactionModel(
                UUID.randomUUID(),
                BigDecimal.valueOf(100.0),
                "Some description",
                LocalDate.now(),
                LocalDateTime.now(),
                null
        );
        Set<?> violations = validator.validate(transaction);
        assertThat(violations).isEmpty();
    }

    @Test
    void purchaseTransactionModelAmountLessThanZeroFailTest() {
        TransactionModel transaction = new TransactionModel(
                UUID.randomUUID(),
                BigDecimal.valueOf(-10.0),
                "Some description",
                LocalDate.now(),
                LocalDateTime.now(),
                null
        );

        Set<ConstraintViolation<TransactionModel>> violations = validator.validate(transaction);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo(TRANSACTION_AMOUNT_MIN_MESSAGE);
    }

    @Test
    void purchaseTransactionModelDescriptionTooLongFailTest() {
        // Arrange
        String longDescription = "Very very very very long description that exceeds the maximum length of 50 characters.";
        TransactionModel transaction = new TransactionModel(
                UUID.randomUUID(),
                BigDecimal.valueOf(100.0),
                longDescription,
                LocalDate.now(),
                LocalDateTime.now(),
                null
        );

        Set<ConstraintViolation<TransactionModel>> violations = validator.validate(transaction);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo(DESCRIPTION_SIZE_MESSAGE);
    }

    @Test
    void purchaseTransactionModelNullAmountFailTest() {
        TransactionModel transaction = new TransactionModel(
                UUID.randomUUID(),
                null,
                "Some description",
                LocalDate.now(),
                LocalDateTime.now(),
                null
        );
        Set<ConstraintViolation<TransactionModel>> violations = validator.validate(transaction);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo(TRANSACTION_AMOUNT_NOT_NULL_MESSAGE);
    }

    @Test
    void purchaseTransactionModelNullDescriptionFailTest() {
        TransactionModel transaction = new TransactionModel(
                UUID.randomUUID(),
                BigDecimal.valueOf(20.00),
                null,
                LocalDate.now(),
                LocalDateTime.now(),
                null
        );
        Set<ConstraintViolation<TransactionModel>> violations = validator.validate(transaction);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo(DESCRIPTION_NOT_NULL_MESSAGE);
    }

    @Test
    void purchaseTransactionModelNullTransactionDateFailTest() {
        TransactionModel transaction = new TransactionModel(
                UUID.randomUUID(),
                BigDecimal.valueOf(20.00),
                "Some description",
                null,
                LocalDateTime.now(),
                null
        );
        Set<ConstraintViolation<TransactionModel>> violations = validator.validate(transaction);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo(TRANSACTION_DATE_NOT_NULL_MESSAGE);
    }

    @Test
    void purchaseTransactionModelFractionalSizeFailTest() {
        TransactionModel transaction = new TransactionModel(
                UUID.randomUUID(),
                BigDecimal.valueOf(20.001),
                "Some description",
                LocalDate.now(),
                LocalDateTime.now(),
                null
        );
        Set<ConstraintViolation<TransactionModel>> violations = validator.validate(transaction);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo(TRANSACTION_AMOUNT_ROUNDED_MESSAGE);
    }

    @Test
    void purchaseTransactionModelGetTransactionRetrieveTest() {
        TransactionRetrieveHistoryModel historyModel = new TransactionRetrieveHistoryModel(
                new TransactionModel(
                        UUID.randomUUID(),
                        BigDecimal.valueOf(20.001),
                        "Some description",
                        LocalDate.now(),
                        LocalDateTime.now(),
                        null
                ),
                "Real",
                1.5,
                BigDecimal.valueOf(100.0)
        );

        List<TransactionRetrieveHistoryModel> history = new ArrayList<>();
        history.add(historyModel);
        TransactionModel transaction = new TransactionModel(
                UUID.randomUUID(),
                BigDecimal.valueOf(20.001),
                "Some description",
                LocalDate.now(),
                LocalDateTime.now(),
                history
        );



        List<TransactionRetrieveHistoryModel> transactionHistory = transaction.getTransactionRetrieveHistory();
        assertEquals(transactionHistory.get(0), historyModel);
    }
}
