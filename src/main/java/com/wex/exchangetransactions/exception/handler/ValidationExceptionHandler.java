package com.wex.exchangetransactions.exception.handler;

import com.wex.exchangetransactions.exception.error.TransactionNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.Objects;

import static com.wex.exchangetransactions.exception.error.DefaultErrorMessages.*;
import static com.wex.exchangetransactions.exception.error.ValidationErrorMessages.DEFAULT_VALIDATION_ERROR;

@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidationExceptionHandler {
    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ProblemDetail> transactionNotFoundExceptionHandler(TransactionNotFoundException ex){
        log.error("ExceptionHandler=transactionNotFoundExceptionHandler error=\"{}\"", ex.getLocalizedMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,ex.getMessage());
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleValidationExceptions(ConstraintViolationException ex) {
        log.error("ExceptionHandler=handleValidationExceptions error=\"{}\"", ex.getLocalizedMessage());
        Iterator<ConstraintViolation<?>> iteratorErrors = ex.getConstraintViolations().iterator();

        String errorMessage;
        if (iteratorErrors.hasNext()){
            errorMessage = iteratorErrors.next().getMessage();
        }else {
            errorMessage = DEFAULT_VALIDATION_ERROR;
        }

        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errorMessage);
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemDetail> handleValidationExceptions(BindException ex) {
        log.error("ExceptionHandler=handleValidationExceptions error=\"{}\"", ex.getLocalizedMessage());

        String errorMessage = null;
        if(!ex.getFieldErrors().isEmpty()) {
            FieldError firstError = ex.getFieldErrors().get(0);
            if(Objects.nonNull(firstError.getDefaultMessage())){
                errorMessage = firstError.getDefaultMessage();
            }
        }

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                Objects.nonNull(errorMessage)?errorMessage:DEFAULT_VALIDATION_ERROR
        );
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ProblemDetail> handleDateTimeParseException(DateTimeParseException ex) {
        log.error("ExceptionHandler=handleDateTimeParseException error=\"{}\"", ex.getLocalizedMessage());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, DEFAULT_DATE_FORMAT_ERROR);
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }
}
