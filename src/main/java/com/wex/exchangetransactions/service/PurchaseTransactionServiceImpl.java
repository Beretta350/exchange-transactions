
package com.wex.exchangetransactions.service;

import com.wex.exchangetransactions.dto.CreatePurchaseTransactionResponseDTO;
import com.wex.exchangetransactions.dto.PurchaseTransactionDTO;
import com.wex.exchangetransactions.exception.error.TransactionNotFoundException;
import com.wex.exchangetransactions.mapper.PurchaseTransactionMapper;
import com.wex.exchangetransactions.model.PurchaseTransactionModel;
import com.wex.exchangetransactions.repository.PurchaseTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseTransactionServiceImpl implements PurchaseTransactionService {
    private final PurchaseTransactionRepository repository;
    @Override
    public CreatePurchaseTransactionResponseDTO createPurchaseTransaction(PurchaseTransactionDTO dto) {
        log.info(
            "Class=TransactionServiceImpl Method=purchaseTransaction amount={} description=\"{}\" transactionDate={}",
            dto.amount(), dto.description(), LocalDate.now()
        );
        PurchaseTransactionModel transaction = PurchaseTransactionMapper.toModel(dto);
        transaction = repository.save(transaction);
        return PurchaseTransactionMapper.toCreateResponseDto(transaction);
    }

    @Override
    public PurchaseTransactionDTO getPurchaseTransactionById(String transactionId) {
        log.info("Class=TransactionServiceImpl Method=purchaseTransaction id={}", transactionId);
        PurchaseTransactionModel transaction = repository.findById(UUID.fromString(transactionId))
                .orElseThrow(TransactionNotFoundException::new);
        return PurchaseTransactionMapper.toDto(transaction);
    }

}
