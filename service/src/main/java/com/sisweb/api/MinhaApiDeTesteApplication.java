package com.sisweb.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableFeignClients
public class MinhaApiDeTesteApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MinhaApiDeTesteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(new BCryptPasswordEncoder().encode("123456"));
	}
}
