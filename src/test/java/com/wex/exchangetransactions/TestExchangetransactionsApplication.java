package com.wex.exchangetransactions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestExchangetransactionsApplication {

	public static void main(String[] args) {
		SpringApplication.from(ExchangetransactionsApplication::main).with(TestExchangetransactionsApplication.class).run(args);
	}

}
