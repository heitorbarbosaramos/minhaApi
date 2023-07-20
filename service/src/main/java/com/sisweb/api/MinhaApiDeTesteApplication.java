package com.sisweb.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.TimeZone;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
@EnableFeignClients
public class MinhaApiDeTesteApplication  extends SpringBootServletInitializer implements InitializingBean {

	private final AppUtil appUtil;
	private final Environment environment;

	@Value("${config.timezone.zone}")
	private String timeZone;

	@PostConstruct
	public void init(){

		TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
		log.info("TIME ZONE {} - {}", TimeZone.getDefault(), LocalDateTime.now());
		log.info("DATA HORA: {}", LocalDateTime.now());
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MinhaApiDeTesteApplication.class);
	}

	public static void main(String[] args) {
		AppUtil.startup(args, MinhaApiDeTesteApplication.class);
	}

	@Override
	public void afterPropertiesSet() {
		AppUtil.checkProfiles(environment, log);
	}

}
