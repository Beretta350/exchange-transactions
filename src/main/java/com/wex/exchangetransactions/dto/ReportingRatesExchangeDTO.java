package com.wex.exchangetransactions.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record ReportingRatesExchangeDTO(
        @JsonProperty("record_date")
        LocalDate recordDate,
        String country,
        String currency,
        @JsonProperty("exchange_rate")
        Double exchangeRate
) {
}
