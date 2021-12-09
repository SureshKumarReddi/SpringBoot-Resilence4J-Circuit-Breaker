package com.Suresh.Controllers;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
public class CircuitBreakerController {

	private static final Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/getMessage")
	@CircuitBreaker(name = "circuit",fallbackMethod = "fallBack" )
	public String getMessage() {
		logger.info(" >>>  inside getMessage() >>>");
		Random r = new Random();
		int nextInt = r.nextInt();
		ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:9098/getMessage?name="+nextInt+"", String.class);
		logger.info(" Response status code  >>>  " +entity.getStatusCode());
		return entity.getBody();
	}
	
	
	public String fallBack(Exception e) {
		logger.info("Service is unavailable .. please try after some time..");
		return "service is down please try after some time";
	}
	
}
