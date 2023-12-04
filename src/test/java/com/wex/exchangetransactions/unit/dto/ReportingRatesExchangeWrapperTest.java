package com.wex.exchangetransactions.unit.dto;

import com.wex.exchangetransactions.dto.ReportingRatesExchangeDTO;
import com.wex.exchangetransactions.dto.ReportingRatesExchangeWrapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReportingRatesExchangeWrapperTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidWrapper() {
        ReportingRatesExchangeDTO dto = new ReportingRatesExchangeDTO(
                LocalDate.now(), "Brazil", "Real", 5.634);
        ReportingRatesExchangeWrapper wrapper = new ReportingRatesExchangeWrapper();
        wrapper.setData(List.of(dto));

        Set<ConstraintViolation<ReportingRatesExchangeWrapper>> violations = validator.validate(wrapper);
        assertTrue(violations.isEmpty());
    }
}
