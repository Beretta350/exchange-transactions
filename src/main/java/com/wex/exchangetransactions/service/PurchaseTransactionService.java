package com.wex.exchangetransactions.service;

import com.wex.exchangetransactions.dto.CreatePurchaseTransactionResponseDTO;
import com.wex.exchangetransactions.dto.PurchaseTransactionDTO;

public interface PurchaseTransactionService {
    CreatePurchaseTransactionResponseDTO createPurchaseTransaction(PurchaseTransactionDTO dto);
    PurchaseTransactionDTO getPurchaseTransactionById(String transactionId);
}
