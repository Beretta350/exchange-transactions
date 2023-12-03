package com.wex.exchangetransactions.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Table(name = "purchase_transaction")
@AllArgsConstructor
public class PurchaseTransactionModel {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private UUID id;
    @NotNull(message = "transaction amount can't be null.")
    @Min(value = 0, message = "the transaction amount must be greater than zero.")
    private Double amount;
    @NotNull(message = "description can't be null.")
    @Size(max = 50, message = "the description must have size between 0 and 50 characters.")
    private String description;
    @NotNull(message = "transaction date can't be null.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate transactionDate;
    @NotNull(message = "transaction timestamp can't be null.")
    private LocalDateTime transactionTimestamp;
}
