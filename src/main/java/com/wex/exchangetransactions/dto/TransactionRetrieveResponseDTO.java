package com.wex.exchangetransactions.dto;

import com.wex.exchangetransactions.annotation.RoundFractionalValue;

import java.time.LocalDate;

import static com.wex.exchangetransactions.exception.error.ValidationErrorMessages.RETRIEVE_CONVERTED_AMOUNT_ROUNDED_MESSAGE;

public record TransactionRetrieveResponseDTO(
        String transactionId,
        String description,
        LocalDate transactionDate,
        Double originalAmount,
        Double exchangeRate,
        @RoundFractionalValue(fractionDigits = 2, message = RETRIEVE_CONVERTED_AMOUNT_ROUNDED_MESSAGE)
        Double convertedAmount) {
    public static TransactionRetrieveResponseDTO buildResponseDTO(
            TransactionResponseDTO purchaseTransaction,
            Double exchangeRate,
            Double convertedAmount
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
