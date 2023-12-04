package com.wex.exchangetransactions.controller;

import com.wex.exchangetransactions.dto.TransactionResponseDTO;
import com.wex.exchangetransactions.dto.TransactionRequestDTO;
import com.wex.exchangetransactions.dto.TransactionRetrieveResponseDTO;
import com.wex.exchangetransactions.service.TransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.wex.exchangetransactions.exception.error.ValidationErrorMessages.RETRIEVE_CURRENCY_NOT_NULL_MESSAGE;
import static com.wex.exchangetransactions.exception.error.ValidationErrorMessages.TRANSACTION_ID_NOT_NULL_MESSAGE;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {

    @Autowired
    private final TransactionService service;

    @PostMapping("/purchase")
    public ResponseEntity<TransactionResponseDTO> purchaseTransaction(
            @RequestBody @Valid TransactionRequestDTO transactionRequestDTO) throws Exception {
        log.info(
            "Class=TransactionController Method=purchaseTransaction amount={} description=\"{}\" transactionDate={}",
            transactionRequestDTO.amount(), transactionRequestDTO.description(), transactionRequestDTO.transactionDate()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createPurchaseTransaction(transactionRequestDTO));
    }

    @GetMapping("/purchase/{id}")
    public ResponseEntity<TransactionResponseDTO> getPurchaseTransactionById(@PathVariable String id) throws Exception {
        log.info("Class=TransactionController Method=getPurchaseTransactionById id={}", id);
        return ResponseEntity.status(HttpStatus.OK).body(service.getPurchaseTransactionById(id));
    }

    @GetMapping("/retrieve")
    public ResponseEntity<TransactionRetrieveResponseDTO> retrievePurchaseTransaction(
            @Valid
            @RequestParam @NotNull(message = TRANSACTION_ID_NOT_NULL_MESSAGE) String id,
            @RequestParam @NotNull(message = RETRIEVE_CURRENCY_NOT_NULL_MESSAGE) String currency,
            @RequestParam(required = false) String country
    ) throws Exception
    {
        log.info("Class=TransactionController Method=retrievePurchaseTransaction id={} currency={} country={}", id, currency, country);
        return ResponseEntity.status(HttpStatus.OK).body(service.retrievePurchaseTransaction(id,currency,country));
    }

}
