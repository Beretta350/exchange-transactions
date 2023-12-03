package com.wex.exchangetransactions.unit.mapper;
import com.wex.exchangetransactions.dto.CreatePurchaseTransactionResponseDTO;
import com.wex.exchangetransactions.dto.PurchaseTransactionDTO;
import com.wex.exchangetransactions.mapper.PurchaseTransactionMapper;
import com.wex.exchangetransactions.model.PurchaseTransactionModel;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PurchaseTransactionMapperTest {

    @Test
    void toCreateResponseDtoTest() {
        UUID transactionId = UUID.randomUUID();
        PurchaseTransactionModel model = new PurchaseTransactionModel(
                transactionId, 12.22, "Test Description", LocalDate.now(), LocalDateTime.now());

        CreatePurchaseTransactionResponseDTO dto = PurchaseTransactionMapper.toCreateResponseDto(model);

        assertNotNull(dto);
        assertEquals(transactionId.toString(), dto.id());
        assertEquals(model.getTransactionTimestamp(), dto.transactionTimestamp());
    }

    @Test
    void toDtoTest() {
        UUID transactionId = UUID.randomUUID();
        PurchaseTransactionModel model = new PurchaseTransactionModel(
                transactionId, 12.22, "Test Description", LocalDate.now(), LocalDateTime.now());

        PurchaseTransactionDTO dto = PurchaseTransactionMapper.toDto(model);

        assertNotNull(dto);
        assertEquals(model.getAmount(), dto.amount());
        assertEquals(model.getDescription(), dto.description());
        assertEquals(model.getTransactionDate(), dto.transactionDate());
    }

    @Test
    void toModelTest() {
        PurchaseTransactionDTO dto =
                new PurchaseTransactionDTO(30.50, "Test Description", LocalDate.now());

        PurchaseTransactionModel model = PurchaseTransactionMapper.toModel(dto);

        assertNotNull(model);
        assertEquals(30.5, model.getAmount());
        assertEquals(dto.description(), model.getDescription());
    }
}
