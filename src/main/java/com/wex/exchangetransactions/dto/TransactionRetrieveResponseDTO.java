package com.wex.exchangetransactions.dto;

import com.wex.exchangetransactions.annotation.RoundFractionalValue;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.wex.exchangetransactions.exception.error.ValidationErrorMessages.RETRIEVE_CONVERTED_AMOUNT_ROUNDED_MESSAGE;

public record TransactionRetrieveResponseDTO(
        String transactionId,
        String description,
        LocalDate transactionDate,
        BigDecimal originalAmount,
        Double exchangeRate,
        @RoundFractionalValue(fractionDigits = 2, message = RETRIEVE_CONVERTED_AMOUNT_ROUNDED_MESSAGE)
        BigDecimal convertedAmount) {
    public static TransactionRetrieveResponseDTO buildResponseDTO(
            TransactionResponseDTO purchaseTransaction,
            Double exchangeRate,
            BigDecimal convertedAmount
    ){
        return new TransactionRetrieveResponseDTO(
                purchaseTransaction.id(),
                purchaseTransaction.description(),
                purchaseTransaction.transactionDate(),
                purchaseTransaction.amount(),
                exchangeRate,
                convertedAmount
        );
    }
}
