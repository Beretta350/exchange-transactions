package com.wex.exchangetransactions.unit.model;

import com.wex.exchangetransactions.model.TransactionModel;
import com.wex.exchangetransactions.model.TransactionRetrieveHistoryModel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static com.wex.exchangetransactions.exception.error.ValidationErrorMessages.RETRIEVE_CONVERTED_AMOUNT_NOT_NULL_MESSAGE;
import static com.wex.exchangetransactions.exception.error.ValidationErrorMessages.RETRIEVE_EXCHANGE_RATE_NOT_NULL_MESSAGE;
import static org.junit.jupiter.api.Assertions.*;

class TransactionRetrieveHistoryModelTest {

    private Validator validator;
    private TransactionModel purchaseTransaction;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        // Initialize any required objects or dependencies
        purchaseTransaction = new TransactionModel(
            UUID.randomUUID(), 12.22, "Test Description", LocalDate.now(), LocalDateTime.now(), null
        );
    }

    @Test
    void testValidModel() {
        TransactionRetrieveHistoryModel historyModel = new TransactionRetrieveHistoryModel(
                purchaseTransaction,
                1.5,
                100.0
        );

        Set<ConstraintViolation<TransactionRetrieveHistoryModel>> violations = validator.validate(historyModel);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidExchangeRate() {
        TransactionRetrieveHistoryModel historyModel = new TransactionRetrieveHistoryModel(
                purchaseTransaction,
                null,  // Invalid: Exchange rate is null
                100.0
        );

        Set<ConstraintViolation<TransactionRetrieveHistoryModel>> violations = validator.validate(historyModel);
        assertEquals(1, violations.size());
        assertEquals(RETRIEVE_EXCHANGE_RATE_NOT_NULL_MESSAGE, violations.iterator().next().getMessage());
    }

    @Test
    void testInvalidConvertedAmount() {
        TransactionRetrieveHistoryModel historyModel = new TransactionRetrieveHistoryModel(
                purchaseTransaction,
                1.5,
                null
        );

        Set<ConstraintViolation<TransactionRetrieveHistoryModel>> violations = validator.validate(historyModel);
        assertEquals(1, violations.size());
        assertEquals(RETRIEVE_CONVERTED_AMOUNT_NOT_NULL_MESSAGE, violations.iterator().next().getMessage());
    }
}
