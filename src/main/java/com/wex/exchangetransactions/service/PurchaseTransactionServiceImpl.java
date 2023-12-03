
package com.wex.exchangetransactions.service;

import com.wex.exchangetransactions.dto.PurchaseTransactionResponseDTO;
import com.wex.exchangetransactions.dto.PurchaseTransactionRequestDTO;
import com.wex.exchangetransactions.exception.error.TransactionNotFoundException;
import com.wex.exchangetransactions.mapper.PurchaseTransactionMapper;
import com.wex.exchangetransactions.model.PurchaseTransactionModel;
import com.wex.exchangetransactions.repository.PurchaseTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseTransactionServiceImpl implements PurchaseTransactionService {
    @Autowired
    private final PurchaseTransactionRepository repository;
    @Override
    public PurchaseTransactionResponseDTO createPurchaseTransaction(PurchaseTransactionRequestDTO dto) {
        log.info(
            "Class=TransactionServiceImpl Method=purchaseTransaction amount={} description=\"{}\" transactionDate={}",
            dto.amount(), dto.description(), LocalDate.now()
        );
        PurchaseTransactionModel transaction = repository.save(PurchaseTransactionMapper.toModel(dto));
        return PurchaseTransactionMapper.toResponseDto(transaction);
    }

    @Override
    public PurchaseTransactionResponseDTO getPurchaseTransactionById(String transactionId) {
        log.info("Class=TransactionServiceImpl Method=purchaseTransaction id={}", transactionId);
        PurchaseTransactionModel transaction = repository.findById(UUID.fromString(transactionId))
                .orElseThrow(TransactionNotFoundException::new);
        return PurchaseTransactionMapper.toResponseDto(transaction);
    }

}
