package com.wex.exchangetransactions.model;

import com.wex.exchangetransactions.annotation.RoundFractionalValue;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.wex.exchangetransactions.exception.error.ValidationErrorMessages.*;

@Entity
@Getter
@Table(name = "purchase_transaction")
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseTransactionModel {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private UUID id;
    @NotNull(message = TRANSACTION_AMOUNT_NOT_NULL_MESSAGE)
    @Min(value = 0, message = TRANSACTION_AMOUNT_MIN_MESSAGE)
    @RoundFractionalValue(fractionDigits = 2, message = TRANSACTION_AMOUNT_ROUNDED_MESSAGE)
    private Double amount;
    @NotNull(message = DESCRIPTION_NOT_NULL_MESSAGE)
    @Size(max = 50, message = DESCRIPTION_SIZE_MESSAGE)
    private String description;
    @NotNull(message = TRANSACTION_DATE_NOT_NULL_MESSAGE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate transactionDate;
    @NotNull(message = TRANSACTION_TIMESTAMP_NOT_NULL_MESSAGE)
    private LocalDateTime transactionTimestamp;
}
