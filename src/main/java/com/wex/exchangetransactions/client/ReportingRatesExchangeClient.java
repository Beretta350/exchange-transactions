package com.wex.exchangetransactions.client;

import com.wex.exchangetransactions.dto.ReportingRatesExchangeDTO;

import java.time.LocalDate;

public interface ReportingRatesExchangeClient {
    ReportingRatesExchangeDTO getExchangeRates(String currency, LocalDate purchaseDate, String country);
}
