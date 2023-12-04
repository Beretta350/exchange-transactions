package com.wex.exchangetransactions.client;

import com.wex.exchangetransactions.dto.ReportingRatesExchangeDTO;
import com.wex.exchangetransactions.dto.ReportingRatesExchangeWrapper;
import com.wex.exchangetransactions.exception.error.ReportingRatesNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class FiscalDataRatesExchangeClient implements ReportingRatesExchangeClient {
    @Autowired
    private final RestTemplate restTemplate;

    @Value("${fiscal.data.rates.exchange.url}")
    private String baseUrl;

    public ReportingRatesExchangeDTO getExchangeRates(String currency, LocalDate purchaseDate, String country){
        log.info("Class=FiscalDataRatesExchangeClient Mehtod=getExchangeRates currency={} purchaseDate={}",
                currency, purchaseDate);
        String request = buildGetRequest(
                currency, country, purchaseDate.toString(), purchaseDate.minusMonths(6).toString()
        );
        log.info("Class=FiscalDataRatesExchangeClient Mehtod=getExchangeRates request={}", request);
        ReportingRatesExchangeWrapper reports = restTemplate.getForObject(request, ReportingRatesExchangeWrapper.class);
        if(Objects.isNull(reports) || Objects.isNull(reports.getData()) || reports.getData().isEmpty()){
            throw new ReportingRatesNotFoundException();
        }

        log.info("Class=FiscalDataRatesExchangeClient Mehtod=getExchangeRates exchangeRate={}", reports.getData().get(0).exchangeRate());
        return reports.getData().get(0);
    }

    private String buildGetRequest(String currency, String country, String purchaseDate, String dateMinus6Months){
        String filterCurrency = "currency:eq:".concat(currency);
        String filterFinalDate = "record_date:lte:".concat(purchaseDate);
        String filterInitDate = "record_date:gte:".concat(dateMinus6Months);
        String filter = String.join(",", filterCurrency,filterInitDate,filterFinalDate);

        if (Objects.nonNull(country)){
            String filterCountry = "country:eq:".concat(country);
            filter = String.join(",", filter,filterCountry);
        }

        return UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("fields", "record_date,country,currency,exchange_rate")
                .queryParam("filter", filter)
                .queryParam("sort", "-record_date")
                .build()
                .toUriString();
    }
}
