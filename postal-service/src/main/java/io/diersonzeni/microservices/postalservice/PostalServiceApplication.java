package io.diersonzeni.microservices.postalservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableEurekaClient
@EnableWebFlux
public class PostalServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostalServiceApplication.class, args);
	}

}
