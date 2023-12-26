package com.wex.exchangetransactions.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.wex.exchangetransactions.dto.ReportingRatesExchangeDTO;
import com.wex.exchangetransactions.dto.TransactionRequestDTO;
import com.wex.exchangetransactions.dto.TransactionResponseDTO;
import com.wex.exchangetransactions.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@WireMockTest(httpPort = 8081)
public class TransactionIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    @Autowired
    protected MockMvc mockMvc;

    public TransactionIntegrationTest() {
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/transaction");
    }

    @Test
    public void testPurchaseTransaction() throws Exception {
        String request = "/transaction/purchase";
        LocalDate transactionDate = LocalDate.now();
        TransactionRequestDTO innerDto =
                new TransactionRequestDTO(BigDecimal.valueOf(20.75), "Test description", transactionDate);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(innerDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("amount").value(20.75))
                .andExpect(jsonPath("description").value("Test description"))
                .andReturn();

    }

    @Test
    public void testGetPurchaseTransaction() throws Exception {
        LocalDate transactionDate = LocalDate.now();
        TransactionRequestDTO innerDto =
                new TransactionRequestDTO(BigDecimal.valueOf(20.75), "Test description", transactionDate);

        String postRequest = "/transaction/purchase";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post(postRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(innerDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("amount").value(20.75))
                .andExpect(jsonPath("description").value("Test description"))
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        TransactionResponseDTO createResponse = new ObjectMapper().readValue(responseContent, TransactionResponseDTO.class);

        String getRequest = "/transaction/purchase/".concat(createResponse.id());

        mockMvc.perform(MockMvcRequestBuilders
                        .get(getRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value(createResponse.id()))
                .andExpect(jsonPath("amount").value(createResponse.amount()))
                .andExpect(jsonPath("description").value(createResponse.description()))
                .andExpect(jsonPath("transactionDate").value(createResponse.transactionDate().toString()))
                .andReturn();
    }

    @Test
    public void testRetrievePurchaseTransaction(WireMockRuntimeInfo wmRuntimeInfo) throws Exception {
        LocalDate transactionDate = LocalDate.now();
        TransactionRequestDTO innerDto =
                new TransactionRequestDTO(BigDecimal.valueOf(20.75), "Test description", transactionDate);

        String postRequest = "/transaction/purchase";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post(postRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(innerDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("amount").value(20.75))
                .andExpect(jsonPath("description").value("Test description"))
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        TransactionResponseDTO createResponse = new ObjectMapper().readValue(responseContent, TransactionResponseDTO.class);

        String requestRetrieve = "/transaction/retrieve?".concat("id="+createResponse.id())
                .concat("&currency=").concat("Real");

        ReportingRatesExchangeDTO exchangeDTO = new ReportingRatesExchangeDTO(
                LocalDate.now(),
                "Brazil",
                "Real",
                5.033
        );
        String body = "{\"data\": [".concat(exchangeDTO.toJSONString()).concat("]}");
        stubFor(get(urlMatching(".*rates_of_exchange.*"))
                .willReturn(
                    aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(body)
                ));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(requestRetrieve))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("transactionId").value(createResponse.id()))
                .andExpect(jsonPath("originalAmount").value(createResponse.amount()))
                .andExpect(jsonPath("description").value(createResponse.description()))
                .andExpect(jsonPath("transactionDate").value(createResponse.transactionDate().toString()))
                .andReturn();
    }
}

