package com.wex.exchangetransactions.unit.mapper;
import com.wex.exchangetransactions.dto.TransactionResponseDTO;
import com.wex.exchangetransactions.dto.TransactionRequestDTO;
import com.wex.exchangetransactions.mapper.TransactionMapper;
import com.wex.exchangetransactions.model.TransactionModel;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionMapperTest {

    @Test
    void toCreateResponseDtoTest() {
        UUID transactionId = UUID.randomUUID();
        TransactionModel model = new TransactionModel(
                transactionId,
                BigDecimal.valueOf(12.22),
                "Test Description",
                LocalDate.now(),
                LocalDateTime.now(),
                null
        );

        TransactionResponseDTO dto = TransactionMapper.toResponseDto(model);

        assertNotNull(dto);
        assertEquals(transactionId.toString(), dto.id());
        assertEquals(model.getTransactionTimestamp(), dto.transactionTimestamp());
    }

    @Test
    void toDtoTest() {
        UUID transactionId = UUID.randomUUID();
        TransactionModel model = new TransactionModel(
                transactionId, BigDecimal.valueOf(12.22), "Test Description", LocalDate.now(), LocalDateTime.now(), null);

        TransactionRequestDTO dto = TransactionMapper.toRequestDto(model);

        assertNotNull(dto);
        assertEquals(model.getAmount(), dto.amount());
        assertEquals(model.getDescription(), dto.description());
        assertEquals(model.getTransactionDate(), dto.transactionDate());
    }

    @Test
    void toModelTest() {
        TransactionRequestDTO dto =
                new TransactionRequestDTO(BigDecimal.valueOf(30.54321), "Test Description", LocalDate.now());

        TransactionModel model = TransactionMapper.toModel(dto);

        assertNotNull(model);
        assertEquals(BigDecimal.valueOf(30.54), model.getAmount());
        assertEquals(dto.description(), model.getDescription());
    }
}
