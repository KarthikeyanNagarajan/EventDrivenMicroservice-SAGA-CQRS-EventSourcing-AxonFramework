package com.karthik.axonsaga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Bean(name = "userclient")
	public WebClient userClient() {
		return WebClient.builder()
				.baseUrl("http://localhost:8081/userservice")
				.build();
	}

	@Bean(name = "productclient")
	public WebClient productClient() {
		return WebClient.builder()
				.baseUrl("http://localhost:8082/productservice")
				.build();
	}
}
