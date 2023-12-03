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
@NoArgsConstructor
public class PurchaseTransactionModel {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private UUID id;
    @NotNull
    @Min(value = 0, message = "the transaction amount must be greater than zero.")
    private Double amount;
    @Size(max = 50, message = "the description must have size between 0 and 50 characters.")
    private String description;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate transactionDate;
    @NotNull
    private LocalDateTime transactionTimestamp;
}
