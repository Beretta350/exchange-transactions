package com.wex.exchangetransactions.integration;

import com.wex.exchangetransactions.dto.TransactionRequestDTO;
import com.wex.exchangetransactions.dto.TransactionResponseDTO;
import com.wex.exchangetransactions.dto.TransactionRetrieveResponseDTO;
import com.wex.exchangetransactions.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class TransactionIntegrationTest {
    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private TransactionRepository h2Repository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/transaction");
    }

    @Test
    public void testPurchaseTransaction() {
        LocalDate transactionDate = LocalDate.now();
        TransactionRequestDTO innerDto =
                new TransactionRequestDTO(20.75, "Test description", transactionDate);

        String request = "/purchase";

        TransactionResponseDTO response =
                restTemplate.postForObject(baseUrl + request, innerDto, TransactionResponseDTO.class);
        assertNotNull(response);
        assertEquals(20.75, response.amount());
        assertEquals("Test description", response.description());
    }

    @Test
    public void testGetPurchaseTransaction() {
        LocalDate transactionDate = LocalDate.now();
        TransactionRequestDTO innerDto =
                new TransactionRequestDTO(20.75, "Test description", transactionDate);

        String request = "/purchase";

        TransactionResponseDTO createResponse =
                restTemplate.postForObject(baseUrl + request, innerDto, TransactionResponseDTO.class);
        assertEquals(20.75, createResponse.amount());
        assertEquals("Test description", createResponse.description());

        request = "/purchase/".concat(createResponse.id());

        TransactionResponseDTO response =
                restTemplate.getForObject(baseUrl + request, TransactionResponseDTO.class);
        assertNotNull(response);
        assertEquals(response.id(), createResponse.id());
        assertEquals(response.amount(), createResponse.amount());
        assertEquals(response.description(), createResponse.description());
        assertEquals(response.transactionDate(), createResponse.transactionDate());
    }

    @Test
    public void testRetrievePurchaseTransaction() {
        LocalDate transactionDate = LocalDate.now();
        TransactionRequestDTO innerDto =
                new TransactionRequestDTO(20.75, "Test description", transactionDate);

        String request = "/purchase";

        TransactionResponseDTO createResponse =
                restTemplate.postForObject(baseUrl + request, innerDto, TransactionResponseDTO.class);
        assertEquals(20.75, createResponse.amount());
        assertEquals("Test description", createResponse.description());

        request = "/retrieve?".concat("id="+createResponse.id())
                .concat("&currency=").concat("Real");

        TransactionRetrieveResponseDTO response =
                restTemplate.getForObject(baseUrl + request, TransactionRetrieveResponseDTO.class);
        assertNotNull(response);
        assertEquals(response.transactionId(), createResponse.id());
        assertEquals(response.originalAmount(), createResponse.amount());
        assertEquals(response.transactionDate(), createResponse.transactionDate());
    }
}

