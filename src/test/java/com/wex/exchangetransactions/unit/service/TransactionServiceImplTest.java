package com.wex.exchangetransactions.unit.service;

import com.wex.exchangetransactions.client.ReportingRatesExchangeClient;
import com.wex.exchangetransactions.dto.ReportingRatesExchangeDTO;
import com.wex.exchangetransactions.dto.TransactionRequestDTO;
import com.wex.exchangetransactions.dto.TransactionResponseDTO;
import com.wex.exchangetransactions.dto.TransactionRetrieveResponseDTO;
import com.wex.exchangetransactions.exception.error.TransactionNotFoundException;
import com.wex.exchangetransactions.model.TransactionModel;
import com.wex.exchangetransactions.model.TransactionRetrieveHistoryModel;
import com.wex.exchangetransactions.repository.TransactionRepository;
import com.wex.exchangetransactions.repository.TransactionRetrieveHistoryRepository;
import com.wex.exchangetransactions.service.TransactionServiceImpl;
import com.wex.exchangetransactions.util.TransactionUtil;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.wex.exchangetransactions.exception.error.ValidationErrorMessages.RETRIEVE_CONVERTED_AMOUNT_NOT_NULL_MESSAGE;
import static com.wex.exchangetransactions.exception.error.ValidationErrorMessages.RETRIEVE_EXCHANGE_RATE_NOT_NULL_MESSAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

    @Mock
    private TransactionRepository repository;

    @Mock
    private TransactionRetrieveHistoryRepository historyRepository;

    @Mock
    private ReportingRatesExchangeClient reportingRatesExchangeClient;


    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void createPurchaseTransactionTest() {
        TransactionRequestDTO inputDto = new TransactionRequestDTO(
                21.25,
                "Test description",
                LocalDate.now()
        );
        TransactionModel mockedSavedTransaction = new TransactionModel(
                UUID.randomUUID(),
                21.25,
                "Test description",
                LocalDate.now(),
                LocalDateTime.now(),
                null
        );

        when(repository.save(any(TransactionModel.class))).thenReturn(mockedSavedTransaction);

        TransactionResponseDTO resultDto = transactionService.createPurchaseTransaction(inputDto);

        verify(repository, times(1)).save(any(TransactionModel.class));
        assertEquals(mockedSavedTransaction.getId().toString(), resultDto.id().toString());
        assertEquals(mockedSavedTransaction.getDescription(), resultDto.description());
        assertEquals(mockedSavedTransaction.getTransactionDate(), resultDto.transactionDate());
        assertEquals(mockedSavedTransaction.getTransactionTimestamp(), resultDto.transactionTimestamp());
    }

    @Test
    void createPurchaseTransactionExceptionTest() {
        TransactionRequestDTO inputDto = new TransactionRequestDTO(
                21.25,
                "Test description",
                LocalDate.now()
        );
        TransactionModel mockedSavedTransaction = new TransactionModel(
                UUID.randomUUID(),
                21.25,
                "Test description",
                LocalDate.now(),
                LocalDateTime.now(),
                null
        );

        when(repository.save(any(TransactionModel.class))).thenThrow(new IllegalArgumentException("Database error"));

        try {
            TransactionResponseDTO resultDto = transactionService.createPurchaseTransaction(inputDto);
        }catch (Exception ex){
            assertEquals(ex.getMessage(), "Database error");
        }


        verify(repository, times(1)).save(any(TransactionModel.class));
    }

    @Test
    void getPurchaseTransactionByIdTest() {
        UUID transactionId = UUID.randomUUID();
        TransactionModel mockedTransaction = new TransactionModel(
                transactionId,
                21.25,
                "Test description",
                LocalDate.now(),
                LocalDateTime.now(),
                null
        );

        when(repository.findById(transactionId)).thenReturn(Optional.of(mockedTransaction));

        TransactionResponseDTO resultDto = transactionService.getPurchaseTransactionById(transactionId.toString());

        verify(repository, times(1)).findById(transactionId);
        assertEquals(mockedTransaction.getId().toString(), resultDto.id().toString());
        assertEquals(mockedTransaction.getDescription(), resultDto.description());
        assertEquals(mockedTransaction.getTransactionDate(), resultDto.transactionDate());
        assertEquals(mockedTransaction.getTransactionTimestamp(), resultDto.transactionTimestamp());
    }

    @Test
    void getPurchaseTransactionByIdNotFoundTest() {
        UUID transactionId = UUID.randomUUID();

        when(repository.findById(transactionId)).thenReturn(Optional.empty());

        assertThrows(TransactionNotFoundException.class, () -> transactionService.getPurchaseTransactionById(transactionId.toString()));

        verify(repository, times(1)).findById(transactionId);
    }

    @Test
    void testRetrievePurchaseTransaction() throws Exception {
        // Arrange
        UUID uuidTransactionId = UUID.randomUUID();
        String transactionId = uuidTransactionId.toString();
        Double exchangeRate = 5.634;
        Double originalAmount = 21.25;
        Double convertedAmount = TransactionUtil.convertAndRoundRetrieve(originalAmount, exchangeRate);
        LocalDate transactionDate = LocalDate.now();
        String currency = "Real";
        String country = "Brazil";

        TransactionModel mockedTransaction = new TransactionModel(
                uuidTransactionId,
                originalAmount,
                "Test description",
                transactionDate,
                LocalDateTime.now(),
                null
        );

        // Mock dependencies
        TransactionResponseDTO purchaseTransactionDTO = new TransactionResponseDTO(
                transactionId,
                21.25,
                "Test description",
                transactionDate,
                LocalDateTime.now()
        );

        TransactionRetrieveHistoryModel historyModel = new TransactionRetrieveHistoryModel(
                1L, "Real", exchangeRate, convertedAmount, LocalDateTime.now(), new TransactionModel()
        );

        ReportingRatesExchangeDTO exchangeRates = new ReportingRatesExchangeDTO(LocalDate.now(), "Brazil", "Real", exchangeRate);

        when(repository.findById(uuidTransactionId)).thenReturn(Optional.of(mockedTransaction));
        when(historyRepository.save(any(TransactionRetrieveHistoryModel.class))).thenReturn(historyModel);
        when(reportingRatesExchangeClient.getExchangeRates(currency, purchaseTransactionDTO.transactionDate(), country))
                .thenReturn(exchangeRates);

        TransactionRetrieveResponseDTO responseDTO = transactionService.retrievePurchaseTransaction(transactionId, currency, country);

        assertNotNull(responseDTO);
        assertEquals(responseDTO.transactionId(), transactionId);
        assertEquals(responseDTO.exchangeRate(), exchangeRate);
        assertEquals(responseDTO.originalAmount(), originalAmount);
        assertEquals(responseDTO.convertedAmount(), convertedAmount);
        assertEquals(responseDTO.transactionDate(), transactionDate);
    }
}
