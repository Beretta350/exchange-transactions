package com.wex.exchangetransactions.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransactionResponseDTO(
        String id,
        BigDecimal amount,
        String description,
        LocalDate transactionDate,
        LocalDateTime transactionTimestamp) {
}
