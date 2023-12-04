package com.wex.exchangetransactions.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransactionResponseDTO(
        String id,
        Double amount,
        String description,
        LocalDate transactionDate,
        LocalDateTime transactionTimestamp) {
}
