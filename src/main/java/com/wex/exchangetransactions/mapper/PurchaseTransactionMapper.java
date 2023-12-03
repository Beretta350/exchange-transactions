package com.wex.exchangetransactions.mapper;

import com.wex.exchangetransactions.dto.CreatePurchaseTransactionResponseDTO;
import com.wex.exchangetransactions.dto.PurchaseTransactionDTO;
import com.wex.exchangetransactions.model.PurchaseTransactionModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Slf4j
public class PurchaseTransactionMapper {

    public static CreatePurchaseTransactionResponseDTO toCreateResponseDto(PurchaseTransactionModel model){
        log.info("Class=PurchaseTransactionMapper Method=toCreateResponseDto id={}", model.getId());
        return new CreatePurchaseTransactionResponseDTO(
                model.getId().toString(),
                model.getTransactionTimestamp());
    }

    public static PurchaseTransactionDTO toDto(PurchaseTransactionModel model){
        log.info("Class=PurchaseTransactionMapper Method=toDto id={}", model.getId());
        return new PurchaseTransactionDTO(
                model.getAmount(),
                model.getDescription(),
                model.getTransactionDate());
    }


    public static PurchaseTransactionModel toModel(PurchaseTransactionDTO dto){
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
