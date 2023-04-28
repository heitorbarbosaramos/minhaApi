package com.sisweb.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MinhaApiDeTesteApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinhaApiDeTesteApplication.class, args);
	}
}
