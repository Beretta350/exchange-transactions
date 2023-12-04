package com.wex.exchangetransactions.exception.error;

public class ValidationErrorMessages {
    private ValidationErrorMessages(){}

    public static final String DEFAULT_VALIDATION_ERROR = "One or more fields passed are not accepted.";
    public static final String TRANSACTION_AMOUNT_NOT_NULL_MESSAGE = "transaction amount can't be null.";
    public static final String TRANSACTION_AMOUNT_MIN_MESSAGE = "transaction amount must be greater or equal than zero.";
    public static final String TRANSACTION_AMOUNT_ROUNDED_MESSAGE = "Transaction amount is not correctly rounded.";
    public static final String DESCRIPTION_NOT_NULL_MESSAGE = "Description can't be null.";
    public static final String DESCRIPTION_SIZE_MESSAGE = "The description must have size between 0 and 50 characters.";
    public static final String TRANSACTION_DATE_NOT_NULL_MESSAGE = "Transaction date can't be null.";
    public static final String TRANSACTION_TIMESTAMP_NOT_NULL_MESSAGE = "Transaction timestamp can't be null.";
    public static final String TRANSACTION_ID_NOT_NULL_MESSAGE = "Transaction id can't be null.";

    public static final String RETRIEVE_EXCHANGE_RATE_NOT_NULL_MESSAGE = "Retrieve exchange rate can't be null.";
    public static final String RETRIEVE_CURRENCY_NOT_NULL_MESSAGE = "Retrieve currency can't be null.";
    public static final String RETRIEVE_CONVERTED_AMOUNT_ROUNDED_MESSAGE = "Retrieve converted amount is not correctly rounded.";
    public static final String RETRIEVE_CONVERTED_AMOUNT_NOT_NULL_MESSAGE = "Retrieve converted amount can't be null.";
}
