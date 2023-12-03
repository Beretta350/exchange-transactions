package com.wex.exchangetransactions;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class ExchangetransactionsApplicationTest {
    @Test
    public void contextLoads() {
        // This test ensures that the Spring context is loaded successfully.
    }
}
