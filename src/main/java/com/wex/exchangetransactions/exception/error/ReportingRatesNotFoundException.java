package com.wex.exchangetransactions.exception.error;

import static com.wex.exchangetransactions.exception.error.DefaultErrorMessages.DEFAULT_NO_REPORTING_RATES_ERROR;

public class ReportingRatesNotFoundException extends RuntimeException {
    public ReportingRatesNotFoundException(){
        super(DEFAULT_NO_REPORTING_RATES_ERROR);
    }
}
