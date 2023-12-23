package com.wex.exchangetransactions.model;

import com.wex.exchangetransactions.annotation.RoundFractionalValue;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.wex.exchangetransactions.exception.error.ValidationErrorMessages.*;

@Entity
@Getter
@Table(name = "transaction")
@AllArgsConstructor
@NoArgsConstructor
public class TransactionModel {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private UUID id;
    @NotNull(message = TRANSACTION_AMOUNT_NOT_NULL_MESSAGE)
    @Min(value = 0, message = TRANSACTION_AMOUNT_MIN_MESSAGE)
    @RoundFractionalValue(fractionDigits = 2, message = TRANSACTION_AMOUNT_ROUNDED_MESSAGE)
    private BigDecimal amount;
    @NotNull(message = DESCRIPTION_NOT_NULL_MESSAGE)
    @Size(max = 50, message = DESCRIPTION_SIZE_MESSAGE)
    private String description;
    @NotNull(message = TRANSACTION_DATE_NOT_NULL_MESSAGE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate transactionDate;
    private LocalDateTime transactionTimestamp;
    @OneToMany(mappedBy = "purchaseTransaction", cascade = CascadeType.ALL)
    private List<TransactionRetrieveHistoryModel> transactionRetrieveHistory;

    @PrePersist
    protected void onCreate() {
        this.transactionTimestamp = LocalDateTime.now();
    }
}
