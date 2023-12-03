package com.wex.exchangetransactions.controller;

import com.wex.exchangetransactions.dto.CreatePurchaseTransactionResponseDTO;
import com.wex.exchangetransactions.dto.PurchaseTransactionDTO;
import com.wex.exchangetransactions.service.PurchaseTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction/purchase")
@RequiredArgsConstructor
@Slf4j
public class PurchaseTransactionController {

    private final PurchaseTransactionService service;

    @PostMapping()
    public ResponseEntity<CreatePurchaseTransactionResponseDTO> purchaseTransaction(
            @RequestBody @Valid PurchaseTransactionDTO purchaseTransactionDTO) {
        log.info(
            "Class=TransactionController Method=createPurchaseTransaction amount={} description=\"{}\" transactionDate={}",
            purchaseTransactionDTO.amount(), purchaseTransactionDTO.description(), purchaseTransactionDTO.transactionDate()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createPurchaseTransaction(purchaseTransactionDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseTransactionDTO> getPurchaseTransactionById(@PathVariable String id) {
        log.info("Class=TransactionController Method=getPurchaseTransactionById id={}", id);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.getPurchaseTransactionById(id));
    }

}
