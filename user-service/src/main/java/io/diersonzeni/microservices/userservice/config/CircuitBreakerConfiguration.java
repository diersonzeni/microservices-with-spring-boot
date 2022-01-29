package io.diersonzeni.microservices.userservice.config;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class CircuitBreakerConfiguration {

	@Bean
    public Customizer<Resilience4JCircuitBreakerFactory> globalCustomConfiguration() {
		
        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(4))
                .build();

        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
        		.slidingWindowSize(10)
				.minimumNumberOfCalls(10)
				.failureRateThreshold(20)
				.permittedNumberOfCallsInHalfOpenState(3)
				.waitDurationInOpenState(Duration.ofMillis(10000))
                .build();

        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(timeLimiterConfig)
                .circuitBreakerConfig(circuitBreakerConfig)
                .build());
    }
}
