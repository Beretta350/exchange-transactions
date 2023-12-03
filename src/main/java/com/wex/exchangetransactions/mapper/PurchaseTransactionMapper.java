package com.wex.exchangetransactions.mapper;

import com.wex.exchangetransactions.dto.PurchaseTransactionResponseDTO;
import com.wex.exchangetransactions.dto.PurchaseTransactionRequestDTO;
import com.wex.exchangetransactions.model.PurchaseTransactionModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Slf4j
public class PurchaseTransactionMapper {

    private PurchaseTransactionMapper() {}

    public static PurchaseTransactionResponseDTO toResponseDto(PurchaseTransactionModel model){
        log.info("Class=PurchaseTransactionMapper Method=toResponseDto id={}", model.getId());
        return new PurchaseTransactionResponseDTO(
                model.getId().toString(),
                model.getAmount(),
                model.getDescription(),
                model.getTransactionDate(),
                model.getTransactionTimestamp());
    }

    public static PurchaseTransactionRequestDTO toRequestDto(PurchaseTransactionModel model){
        log.info("Class=PurchaseTransactionMapper Method=toRequestDto id={}", model.getId());
        return new PurchaseTransactionRequestDTO(
                model.getAmount(),
                model.getDescription(),
                model.getTransactionDate());
    }


    public static PurchaseTransactionModel toModel(PurchaseTransactionRequestDTO dto){
        log.info(
            "Class=PurchaseTransactionMapper Method=toModel amount={} description={}",
            dto.amount(), dto.description()
        );
        Double amount = (double) Math.round(dto.amount() * 100) / 100;
        return new PurchaseTransactionModel(
                null,
                amount,
                dto.description(),
                LocalDate.now(),
                LocalDateTime.now());
    }
}
