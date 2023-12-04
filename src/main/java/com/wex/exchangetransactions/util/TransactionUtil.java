package com.wex.exchangetransactions.util;

import com.wex.exchangetransactions.dto.ReportingRatesExchangeDTO;
import com.wex.exchangetransactions.dto.TransactionResponseDTO;
import com.wex.exchangetransactions.model.TransactionModel;
import com.wex.exchangetransactions.service.TransactionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TransactionUtil {
    private TransactionUtil(){}
    public static Double convertAndRoundRetrieve(Double originalAmount,
                                                 Double exchangeRate){
        log.info("Class=TransactionUtil Method=convertAndRoundRetrieve originalAmount={} exchangeRate={}",
                originalAmount,exchangeRate);
        double convertedAmount = originalAmount * exchangeRate;
        return roundAmount(convertedAmount);
    }

    public static Double roundAmount(Double amount){
        log.info("Class=TransactionUtil Method=roundAmount amount={}", amount);
        return (double) Math.round(amount * 100) / 100;
    }
}
