package com.wex.exchangetransactions.mapper;

import com.wex.exchangetransactions.dto.TransactionResponseDTO;
import com.wex.exchangetransactions.dto.TransactionRequestDTO;
import com.wex.exchangetransactions.model.TransactionModel;
import com.wex.exchangetransactions.util.TransactionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class TransactionMapper {

    private TransactionMapper() {}

    public static TransactionResponseDTO toResponseDto(TransactionModel model){
        log.info("Class=PurchaseTransactionMapper Method=toResponseDto id={}", model.getId());
        return new TransactionResponseDTO(
                model.getId().toString(),
                model.getAmount(),
                model.getDescription(),
                model.getTransactionDate(),
                model.getTransactionTimestamp());
    }

    public static TransactionRequestDTO toRequestDto(TransactionModel model){
        log.info("Class=PurchaseTransactionMapper Method=toRequestDto id={}", model.getId());
        return new TransactionRequestDTO(
                model.getAmount(),
                model.getDescription(),
                model.getTransactionDate());
    }


    public static TransactionModel toModel(TransactionRequestDTO dto){
        log.info(
            "Class=PurchaseTransactionMapper Method=toModel amount={} description=\"{}\"",
            dto.amount(), dto.description()
        );
        return new TransactionModel(
                null,
                TransactionUtil.roundAmount(dto.amount()),
                dto.description(),
                dto.transactionDate(),
                null,
                null);
    }

    public static TransactionModel toModel(TransactionResponseDTO dto){
        log.info(
                "Class=PurchaseTransactionMapper Method=toModel amount={} description=\"{}\"",
                dto.amount(), dto.description()
        );
        return new TransactionModel(
                UUID.fromString(dto.id()),
                TransactionUtil.roundAmount(dto.amount()),
                dto.description(),
                dto.transactionDate(),
                dto.transactionTimestamp(),
                null);
    }
}
