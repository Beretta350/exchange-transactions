package com.wex.exchangetransactions.unit.mapper;
import com.wex.exchangetransactions.dto.PurchaseTransactionResponseDTO;
import com.wex.exchangetransactions.dto.PurchaseTransactionRequestDTO;
import com.wex.exchangetransactions.mapper.PurchaseTransactionMapper;
import com.wex.exchangetransactions.model.PurchaseTransactionModel;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PurchaseTransactionMapperTest {

    @Test
    void toCreateResponseDtoTest() {
        UUID transactionId = UUID.randomUUID();
        PurchaseTransactionModel model = new PurchaseTransactionModel(
                transactionId, 12.22, "Test Description", LocalDate.now(), LocalDateTime.now());

        PurchaseTransactionResponseDTO dto = PurchaseTransactionMapper.toResponseDto(model);

        assertNotNull(dto);
        assertEquals(transactionId.toString(), dto.id());
        assertEquals(model.getTransactionTimestamp(), dto.transactionTimestamp());
    }

    @Test
    void toDtoTest() {
        UUID transactionId = UUID.randomUUID();
        PurchaseTransactionModel model = new PurchaseTransactionModel(
                transactionId, 12.22, "Test Description", LocalDate.now(), LocalDateTime.now());

        PurchaseTransactionRequestDTO dto = PurchaseTransactionMapper.toRequestDto(model);

        assertNotNull(dto);
        assertEquals(model.getAmount(), dto.amount());
        assertEquals(model.getDescription(), dto.description());
        assertEquals(model.getTransactionDate(), dto.transactionDate());
    }

    @Test
    void toModelTest() {
        PurchaseTransactionRequestDTO dto =
                new PurchaseTransactionRequestDTO(30.54321, "Test Description", LocalDate.now());

        PurchaseTransactionModel model = PurchaseTransactionMapper.toModel(dto);

        assertNotNull(model);
        assertEquals(30.54, model.getAmount());
        assertEquals(dto.description(), model.getDescription());
    }
}
