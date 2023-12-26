package com.wex.exchangetransactions.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransactionResponseDTO(
        String id,
        BigDecimal amount,
        String description,
        @JsonDeserialize(using = LocalDateDeserializer.class)
        LocalDate transactionDate,
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        LocalDateTime transactionTimestamp) {
}
