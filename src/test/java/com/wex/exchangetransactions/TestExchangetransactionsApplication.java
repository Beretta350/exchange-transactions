package com.wex.exchangetransactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ActiveProfiles;

@TestConfiguration(proxyBeanMethods = false)
public class TestExchangetransactionsApplication {

	public static void main(String[] args) {
		SpringApplication.from(ExchangetransactionsApplication::main).with(TestExchangetransactionsApplication.class).run(args);
	}

}
