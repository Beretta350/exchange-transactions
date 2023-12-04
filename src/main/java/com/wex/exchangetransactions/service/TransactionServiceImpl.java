
package com.wex.exchangetransactions.service;

import com.wex.exchangetransactions.client.ReportingRatesExchangeClient;
import com.wex.exchangetransactions.dto.TransactionResponseDTO;
import com.wex.exchangetransactions.dto.TransactionRequestDTO;
import com.wex.exchangetransactions.dto.ReportingRatesExchangeDTO;
import com.wex.exchangetransactions.dto.TransactionRetrieveResponseDTO;
import com.wex.exchangetransactions.exception.error.TransactionNotFoundException;
import com.wex.exchangetransactions.mapper.TransactionMapper;
import com.wex.exchangetransactions.model.TransactionModel;
import com.wex.exchangetransactions.model.TransactionRetrieveHistoryModel;
import com.wex.exchangetransactions.repository.TransactionRepository;
import com.wex.exchangetransactions.repository.TransactionRetrieveHistoryRepository;
import com.wex.exchangetransactions.util.TransactionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private final TransactionRepository repository;
    @Autowired
    private final TransactionRetrieveHistoryRepository historyRepository;
    @Autowired
    private final ReportingRatesExchangeClient reportingRatesExchangeClient;
    @Override
    public TransactionResponseDTO createPurchaseTransaction(TransactionRequestDTO dto) {
        log.info(
            "Class=TransactionServiceImpl Method=purchaseTransaction amount={} description=\"{}\" transactionDate={}",
            dto.amount(), dto.description(), dto.transactionDate()
        );
        TransactionModel transaction = repository.save(TransactionMapper.toModel(dto));
        return TransactionMapper.toResponseDto(transaction);
    }

    @Override
    public TransactionResponseDTO getPurchaseTransactionById(String transactionId) {
        log.info("Class=TransactionServiceImpl Method=purchaseTransaction id={}", transactionId);
        TransactionModel transaction = repository.findById(UUID.fromString(transactionId))
                .orElseThrow(TransactionNotFoundException::new);
        return TransactionMapper.toResponseDto(transaction);
    }

    @Override
    public TransactionRetrieveResponseDTO retrievePurchaseTransaction(String id, String currency, String country) throws Exception {
        log.info("Class=TransactionServiceImpl Method=retrievePurchaseTransaction id={}", id);
        TransactionResponseDTO purchaseTransactionDTO = getPurchaseTransactionById(id);

        ReportingRatesExchangeDTO exchangeRates =
                reportingRatesExchangeClient.getExchangeRates(currency, purchaseTransactionDTO.transactionDate(), country);

        TransactionModel purchaseTransaction = TransactionMapper.toModel(purchaseTransactionDTO);
        Double convertedAmount = TransactionUtil.convertAndRoundRetrieve(
                purchaseTransaction.getAmount(),
                exchangeRates.exchangeRate()
        );
        TransactionRetrieveHistoryModel retrieveHistory =
                new TransactionRetrieveHistoryModel(purchaseTransaction, exchangeRates.exchangeRate(), convertedAmount);
        historyRepository.save(retrieveHistory);
        return TransactionRetrieveResponseDTO.buildResponseDTO(purchaseTransactionDTO, exchangeRates.exchangeRate(), convertedAmount);
    }

}
