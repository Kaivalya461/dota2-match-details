package com.kv.dota2matchdetails;

import com.kv.config.RestTemplateInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication(scanBasePackages = "com.kv")
public class Dota2MatchDetailsApplication {

	public static void main(String[] args) {
		SpringApplication.run(Dota2MatchDetailsApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setInterceptors(List.of(new RestTemplateInterceptor()));
		return restTemplate;
	}
}
