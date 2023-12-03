package com.wex.exchangetransactions.service;

import com.wex.exchangetransactions.dto.PurchaseTransactionResponseDTO;
import com.wex.exchangetransactions.dto.PurchaseTransactionRequestDTO;

public interface PurchaseTransactionService {
    PurchaseTransactionResponseDTO createPurchaseTransaction(PurchaseTransactionRequestDTO dto) throws Exception;
    PurchaseTransactionResponseDTO getPurchaseTransactionById(String transactionId) throws Exception;
}
