package com.wex.exchangetransactions.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.wex.exchangetransactions.exception.error.ErrorMessages.*;

@RestControllerAdvice
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
public class DefaultExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> globalExceptionHandler(Exception ex){
        log.error("ExceptionHandler=globalExceptionHandler error=\"{}\"", ex.getLocalizedMessage());

        String errorMessage;
        if (Objects.nonNull(ex.getMessage())){
            errorMessage = ex.getMessage();
        }else {
            errorMessage = DEFAULT_INTERNAL_SERVER_ERROR;
        }

        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
        problemDetail.setProperty("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }
}
