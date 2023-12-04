package com.wex.exchangetransactions.model;

import com.wex.exchangetransactions.dto.ReportingRatesExchangeDTO;
import com.wex.exchangetransactions.util.TransactionUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import static com.wex.exchangetransactions.exception.error.ValidationErrorMessages.*;
@Entity
@Table(name = "transaction_retrieve_history")
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRetrieveHistoryModel {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = RETRIEVE_CURRENCY_NOT_NULL_MESSAGE)
    private String currency;

    @NotNull(message = RETRIEVE_EXCHANGE_RATE_NOT_NULL_MESSAGE)
    private Double exchangeRate;

    @NotNull(message = RETRIEVE_CONVERTED_AMOUNT_NOT_NULL_MESSAGE)
    private Double convertedAmount;

    private LocalDateTime retrieveTimestamp;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private TransactionModel purchaseTransaction;

    @PrePersist
    protected void onCreate() {
        this.retrieveTimestamp = LocalDateTime.now();
    }

    public TransactionRetrieveHistoryModel(
            TransactionModel purchaseTransaction,
            String currency,
            Double exchangeRate,
            Double convertedAmount){
        this.id = null;
        this.exchangeRate = exchangeRate;
        this.currency=currency;
        this.convertedAmount = convertedAmount;
        this.retrieveTimestamp = null;
        this.purchaseTransaction = purchaseTransaction;
    }
}
