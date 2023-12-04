package com.wex.exchangetransactions.service;

import com.wex.exchangetransactions.dto.TransactionResponseDTO;
import com.wex.exchangetransactions.dto.TransactionRequestDTO;
import com.wex.exchangetransactions.dto.TransactionRetrieveResponseDTO;

public interface TransactionService {
    TransactionResponseDTO createPurchaseTransaction(TransactionRequestDTO dto) throws Exception;
    TransactionResponseDTO getPurchaseTransactionById(String transactionId) throws Exception;
    TransactionRetrieveResponseDTO retrievePurchaseTransaction(String id, String currency, String country) throws Exception;
}
