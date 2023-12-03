package com.wex.exchangetransactions.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wex.exchangetransactions.controller.PurchaseTransactionController;
import com.wex.exchangetransactions.dto.PurchaseTransactionRequestDTO;
import com.wex.exchangetransactions.dto.PurchaseTransactionResponseDTO;
import com.wex.exchangetransactions.exception.error.TransactionNotFoundException;
import com.wex.exchangetransactions.exception.handler.DefaultExceptionHandler;
import com.wex.exchangetransactions.exception.handler.ValidationExceptionHandler;
import com.wex.exchangetransactions.model.PurchaseTransactionModel;
import com.wex.exchangetransactions.service.PurchaseTransactionService;
import jakarta.validation.*;
import org.hibernate.TransactionManagementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.wex.exchangetransactions.exception.error.DefaultErrorMessages.*;
import static com.wex.exchangetransactions.exception.error.ValidationErrorMessages.DEFAULT_VALIDATION_ERROR;
import static com.wex.exchangetransactions.exception.error.ValidationErrorMessages.TRANSACTION_AMOUNT_NOT_NULL_MESSAGE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class PurchaseTransactionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PurchaseTransactionService service;

    @InjectMocks
    private PurchaseTransactionController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new DefaultExceptionHandler(), new ValidationExceptionHandler())
                .build();
    }

    @Test
    void testPurchaseTransaction() throws Exception {
        PurchaseTransactionRequestDTO requestDTO = new PurchaseTransactionRequestDTO(
                21.25,
                "Test description",
                LocalDate.now()
        );
        String transactionId = UUID.randomUUID().toString();
        PurchaseTransactionResponseDTO responseDTO = new PurchaseTransactionResponseDTO(
                transactionId,
                21.25,
                "Test description",
                LocalDate.now(),
                LocalDateTime.now()
        );

        when(service.createPurchaseTransaction(any(PurchaseTransactionRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/transaction/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value(transactionId))
                .andReturn();
    }



    @Test
    void testGetPurchaseTransactionById() throws Exception {
        String transactionId = UUID.randomUUID().toString();
        PurchaseTransactionResponseDTO responseDTO = new PurchaseTransactionResponseDTO(
                transactionId,
                21.25,
                "Test description",
                LocalDate.now(),
                LocalDateTime.now()
        );

        when(service.getPurchaseTransactionById(transactionId)).thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/transaction/purchase/{id}", transactionId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value(transactionId))
                .andReturn();
    }

    @Test
    void testPurchaseTransactionWithInternalServerError() throws Exception {
        PurchaseTransactionRequestDTO requestDTO = new PurchaseTransactionRequestDTO(
                21.25,
                "Test description",
                LocalDate.now()
        );

        doThrow(new RuntimeException("Simulated error"))
                .when(service).createPurchaseTransaction(any(PurchaseTransactionRequestDTO.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/transaction/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("status").value(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andReturn();
    }

    @Test
    void testGetPurchaseTransactionByIdWithInternalServerError() throws Exception {
        String transactionId = UUID.randomUUID().toString();

        doThrow(new RuntimeException("Simulated error"))
                .when(service).getPurchaseTransactionById(transactionId);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/transaction/purchase/{id}", transactionId))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("status").value(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andReturn();
    }

    @Test
    void testGetPurchaseTransactionByIdWithTransactionNotFoundError() throws Exception {
        String transactionId = UUID.randomUUID().toString();

        doThrow(new TransactionNotFoundException())
                .when(service).getPurchaseTransactionById(transactionId);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/transaction/purchase/{id}", transactionId))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("detail").value(DEFAULT_TRANSACTION_NOT_FOUND_ERROR))
                .andReturn();
    }

    @Test
    void testValidationExceptionHandlerSpecificBindException() throws Exception {
        PurchaseTransactionRequestDTO requestDTO = new PurchaseTransactionRequestDTO(
                null,
                "Test description",
                LocalDate.now()
        );

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/transaction/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("detail").value(TRANSACTION_AMOUNT_NOT_NULL_MESSAGE))
                .andReturn();
    }

    @Test
    void testValidationExceptionHandlerDefaultBindEmptyErrorsException() throws Exception {
        PurchaseTransactionRequestDTO requestDTO = new PurchaseTransactionRequestDTO(
                20.25,
                "Test description",
                LocalDate.now()
        );

        BindException bindException = new BindException(requestDTO, "purchaseTransactionRequestDTO");
        doThrow(bindException).when(service).createPurchaseTransaction(any(PurchaseTransactionRequestDTO.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/transaction/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("detail").value(DEFAULT_VALIDATION_ERROR))
                .andReturn();
    }

    @Test
    void testValidationExceptionHandlerDefaultBindException() throws Exception {
        PurchaseTransactionRequestDTO requestDTO = new PurchaseTransactionRequestDTO(
                20.25,
                "Test description",
                LocalDate.now()
        );

        // Inject a mock BindException to simulate a validation error
        BindException bindException = new BindException(requestDTO, "purchaseTransactionRequestDTO");
        bindException.addError(new FieldError("purchaseTransactionRequestDTO", "amount", DEFAULT_VALIDATION_ERROR));

        doThrow(bindException).when(service).createPurchaseTransaction(any(PurchaseTransactionRequestDTO.class));


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/transaction/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("detail").value(DEFAULT_VALIDATION_ERROR))
                .andReturn();
    }

    @Test
    void testValidationExceptionHandlerDefaultBindWithNullMessageException() throws Exception {
        PurchaseTransactionRequestDTO requestDTO = new PurchaseTransactionRequestDTO(
                20.25,
                "Test description",
                LocalDate.now()
        );

        // Inject a mock BindException to simulate a validation error
        BindException bindException = new BindException(requestDTO, "purchaseTransactionRequestDTO");
        bindException.addError(new FieldError("purchaseTransactionRequestDTO", "amount", null));

        doThrow(bindException).when(service).createPurchaseTransaction(any(PurchaseTransactionRequestDTO.class));


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/transaction/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("detail").value(DEFAULT_VALIDATION_ERROR))
                .andReturn();
    }

    @Test
    void testValidationExceptionHandlerSpecificConstraintViolation() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        PurchaseTransactionRequestDTO requestDTO = new PurchaseTransactionRequestDTO(
                21.25,
                "Test description",
                LocalDate.now()
        );
        PurchaseTransactionModel mockedSavedTransaction = new PurchaseTransactionModel(
                UUID.randomUUID(),
                null,
                "Test description",
                LocalDate.now(),
                LocalDateTime.now()
        );
        Set<ConstraintViolation<PurchaseTransactionModel>> violations = validator.validate(mockedSavedTransaction);

        ConstraintViolationException exception = new ConstraintViolationException(new HashSet<>(violations));
        doThrow(exception).when(service).createPurchaseTransaction(any(PurchaseTransactionRequestDTO.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/transaction/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("detail").value(TRANSACTION_AMOUNT_NOT_NULL_MESSAGE))
                .andReturn();
    }

    @Test
    void testValidationExceptionHandlerDefaultConstraintViolation() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        PurchaseTransactionRequestDTO requestDTO = new PurchaseTransactionRequestDTO(
                21.25,
                "Test description",
                LocalDate.now()
        );
        PurchaseTransactionModel mockedSavedTransaction = new PurchaseTransactionModel(
                UUID.randomUUID(),
                21.25,
                "Test description",
                LocalDate.now(),
                LocalDateTime.now()
        );
        Set<ConstraintViolation<PurchaseTransactionModel>> violations = validator.validate(mockedSavedTransaction);

        ConstraintViolationException exception = new ConstraintViolationException(new HashSet<>(violations));
        doThrow(exception).when(service).createPurchaseTransaction(any(PurchaseTransactionRequestDTO.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/transaction/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("detail").value(DEFAULT_VALIDATION_ERROR))
                .andReturn();
    }

    @Test
    void testValidationExceptionHandlerDateTimeParseException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/transaction/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                            "{\"amount\":21.25,\"description\":\"Test description\", \"transactionDate\":\"2023/12/01\"}"
                        ))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("detail").value(DEFAULT_DATE_FORMAT_ERROR))
                .andReturn();
    }
}
