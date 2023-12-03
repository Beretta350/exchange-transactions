package com.wex.exchangetransactions.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PurchaseTransactionDTO(
        @Min(value = 0, message = "the transaction amount must be greater than zero.")
        Double amount,
        @Size(max = 50, message = "the description must have size between 0 and 50 characters.")
        String description,
        @JsonSerialize(using = LocalDateSerializer.class)
        @JsonDeserialize(using = LocalDateDeserializer.class)
        LocalDate transactionDate) {
}
