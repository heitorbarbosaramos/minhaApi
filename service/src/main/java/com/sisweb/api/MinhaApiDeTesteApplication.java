package com.sisweb.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication
@EnableFeignClients
public class MinhaApiDeTesteApplication {

	@Value("${config.timezone.zone}")
	private String timeZone;
	@PostConstruct
	public void init(){

		TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
		log.info("TIME ZONE {} - {}", TimeZone.getDefault(), LocalDateTime.now());
		log.info("DATA HORA: {}", LocalDateTime.now());
	}

	public static void main(String[] args) {
		SpringApplication.run(MinhaApiDeTesteApplication.class, args);
	}
}
