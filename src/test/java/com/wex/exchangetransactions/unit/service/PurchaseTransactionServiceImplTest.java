package com.wex.exchangetransactions.unit.service;

import com.wex.exchangetransactions.dto.PurchaseTransactionResponseDTO;
import com.wex.exchangetransactions.dto.PurchaseTransactionRequestDTO;
import com.wex.exchangetransactions.exception.error.TransactionNotFoundException;
import com.wex.exchangetransactions.mapper.PurchaseTransactionMapper;
import com.wex.exchangetransactions.model.PurchaseTransactionModel;
import com.wex.exchangetransactions.repository.PurchaseTransactionRepository;
import com.wex.exchangetransactions.service.PurchaseTransactionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PurchaseTransactionServiceImplTest {

    @Mock
    private PurchaseTransactionRepository repository;

    @InjectMocks
    private PurchaseTransactionServiceImpl transactionService;

    @Test
    void createPurchaseTransactionTest() {
        PurchaseTransactionRequestDTO inputDto = new PurchaseTransactionRequestDTO(
                21.25,
                "Test description",
                LocalDate.now()
        );
        PurchaseTransactionModel mockedSavedTransaction = new PurchaseTransactionModel(
                UUID.randomUUID(),
                21.25,
                "Test description",
                LocalDate.now(),
                LocalDateTime.now()
        );

        when(repository.save(any(PurchaseTransactionModel.class))).thenReturn(mockedSavedTransaction);

        PurchaseTransactionResponseDTO resultDto = transactionService.createPurchaseTransaction(inputDto);

        verify(repository, times(1)).save(any(PurchaseTransactionModel.class));
        assertEquals(mockedSavedTransaction.getId().toString(), resultDto.id().toString());
        assertEquals(mockedSavedTransaction.getDescription(), resultDto.description());
        assertEquals(mockedSavedTransaction.getTransactionDate(), resultDto.transactionDate());
        assertEquals(mockedSavedTransaction.getTransactionTimestamp(), resultDto.transactionTimestamp());
    }

    @Test
    void createPurchaseTransactionExceptionTest() {
        PurchaseTransactionRequestDTO inputDto = new PurchaseTransactionRequestDTO(
                21.25,
                "Test description",
                LocalDate.now()
        );
        PurchaseTransactionModel mockedSavedTransaction = new PurchaseTransactionModel(
                UUID.randomUUID(),
                21.25,
                "Test description",
                LocalDate.now(),
                LocalDateTime.now()
        );

        when(repository.save(any(PurchaseTransactionModel.class))).thenThrow(new IllegalArgumentException("Database error"));

        try {
            PurchaseTransactionResponseDTO resultDto = transactionService.createPurchaseTransaction(inputDto);
        }catch (Exception ex){
            assertEquals(ex.getMessage(), "Database error");
        }


        verify(repository, times(1)).save(any(PurchaseTransactionModel.class));
    }

    @Test
    void getPurchaseTransactionByIdTest() {
        UUID transactionId = UUID.randomUUID();
        PurchaseTransactionModel mockedTransaction = new PurchaseTransactionModel(
                transactionId,
                21.25,
                "Test description",
                LocalDate.now(),
                LocalDateTime.now()
        );

        when(repository.findById(transactionId)).thenReturn(Optional.of(mockedTransaction));

        PurchaseTransactionResponseDTO resultDto = transactionService.getPurchaseTransactionById(transactionId.toString());

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
}
