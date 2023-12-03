package com.wex.exchangetransactions.exception.error;

import static com.wex.exchangetransactions.exception.error.DefaultErrorMessages.DEFAULT_TRANSACTION_NOT_FOUND_ERROR;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(){
        super(DEFAULT_TRANSACTION_NOT_FOUND_ERROR);
    }
}
