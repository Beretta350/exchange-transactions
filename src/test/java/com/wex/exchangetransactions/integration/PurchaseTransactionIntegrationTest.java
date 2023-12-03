package com.wex.exchangetransactions.integration;

import com.wex.exchangetransactions.dto.PurchaseTransactionRequestDTO;
import com.wex.exchangetransactions.dto.PurchaseTransactionResponseDTO;
import com.wex.exchangetransactions.repository.PurchaseTransactionRepository;
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
public class PurchaseTransactionIntegrationTest {
    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private PurchaseTransactionRepository h2Repository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/transaction/purchase");
    }

    @Test
    public void testPurchaseTransaction() {
        LocalDate transactionDate = LocalDate.now();
        PurchaseTransactionRequestDTO innerDto =
                new PurchaseTransactionRequestDTO(20.75, "Test description", transactionDate);
        PurchaseTransactionResponseDTO response =
                restTemplate.postForObject(baseUrl, innerDto, PurchaseTransactionResponseDTO.class);
        assertNotNull(response);
        assertEquals(20.75, response.amount());
        assertEquals("Test description", response.description());
    }

    @Test
    public void testGetPurchaseTransaction() {
        LocalDate transactionDate = LocalDate.now();
        PurchaseTransactionRequestDTO innerDto =
                new PurchaseTransactionRequestDTO(20.75, "Test description", transactionDate);
        PurchaseTransactionResponseDTO createResponse =
                restTemplate.postForObject(baseUrl, innerDto, PurchaseTransactionResponseDTO.class);
        assertEquals(20.75, createResponse.amount());
        assertEquals("Test description", createResponse.description());

        PurchaseTransactionResponseDTO response =
                restTemplate.getForObject(baseUrl + "/" + createResponse.id(), PurchaseTransactionResponseDTO.class);
        assertNotNull(response);
        assertEquals(response.id(), createResponse.id());
        assertEquals(response.amount(), createResponse.amount());
        assertEquals(response.description(), createResponse.description());
        assertEquals(response.transactionDate(), createResponse.transactionDate());
    }
}

