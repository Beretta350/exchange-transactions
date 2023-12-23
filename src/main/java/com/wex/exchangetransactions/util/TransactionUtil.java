package com.wex.exchangetransactions.util;

import com.wex.exchangetransactions.dto.ReportingRatesExchangeDTO;
import com.wex.exchangetransactions.dto.TransactionResponseDTO;
import com.wex.exchangetransactions.model.TransactionModel;
import com.wex.exchangetransactions.service.TransactionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@Slf4j
public class TransactionUtil {
    private TransactionUtil(){}
    public static BigDecimal convertAndRoundRetrieve(BigDecimal originalAmount,
                                                 Double exchangeRate){
        log.info("Class=TransactionUtil Method=convertAndRoundRetrieve originalAmount={} exchangeRate={}",
                originalAmount,exchangeRate);
        return BigDecimal.valueOf(originalAmount.doubleValue() * exchangeRate)
                .setScale(2, RoundingMode.HALF_EVEN);
    }
}
