package com.wex.exchangetransactions.exception.error;

public class DefaultErrorMessages {
    private DefaultErrorMessages(){}
    public static final String DEFAULT_INTERNAL_SERVER_ERROR = "An error occurred while processing";
    public static final String DEFAULT_TRANSACTION_NOT_FOUND_ERROR = "This transaction does not exists.";
    public static final String DEFAULT_DATE_FORMAT_ERROR = "Invalid date format, please follow this format: yyyy-MM-dd.";
    public static final String DEFAULT_NO_REPORTING_RATES_ERROR = "No reporting rates are available for this currency on the specified date.";

    public static final String DEFAULT_MISSING_PARAMETERS_ERROR = "One or more parameters are missing.";



}
