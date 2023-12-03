package com.wex.exchangetransactions.controller;

import com.wex.exchangetransactions.dto.PurchaseTransactionResponseDTO;
import com.wex.exchangetransactions.dto.PurchaseTransactionRequestDTO;
import com.wex.exchangetransactions.service.PurchaseTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction/purchase")
@RequiredArgsConstructor
@Slf4j
public class PurchaseTransactionController {

    @Autowired
    private final PurchaseTransactionService service;

    @PostMapping()
    public ResponseEntity<PurchaseTransactionResponseDTO> purchaseTransaction(
            @RequestBody @Valid PurchaseTransactionRequestDTO purchaseTransactionRequestDTO) {
        log.info(
            "Class=TransactionController Method=createPurchaseTransaction amount={} description=\"{}\" transactionDate={}",
            purchaseTransactionRequestDTO.amount(), purchaseTransactionRequestDTO.description(), purchaseTransactionRequestDTO.transactionDate()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createPurchaseTransaction(purchaseTransactionRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseTransactionResponseDTO> getPurchaseTransactionById(@PathVariable String id) {
        log.info("Class=TransactionController Method=getPurchaseTransactionById id={}", id);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.getPurchaseTransactionById(id));
    }

}
