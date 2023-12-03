package com.wex.exchangetransactions.dto;

import java.time.LocalDateTime;

public record CreatePurchaseTransactionResponseDTO(
        String id,
        LocalDateTime transactionTimestamp) {
}
