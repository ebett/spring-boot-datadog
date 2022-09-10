package com.example.apiexample;

import com.timgroup.statsd.NonBlockingStatsDClientBuilder;
import com.timgroup.statsd.StatsDClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApiExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiExampleApplication.class, args);
	}

	@Bean
	public StatsDClient statsDClient() {
		 return new NonBlockingStatsDClientBuilder()
				 .prefix("api-example").build();
	}
}
