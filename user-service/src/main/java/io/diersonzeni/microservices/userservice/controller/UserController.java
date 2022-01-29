package io.diersonzeni.microservices.userservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.json.JsonMapper;

import io.diersonzeni.microservices.userservice.dto.User;
import io.diersonzeni.microservices.userservice.exception.GenericException;
import io.diersonzeni.microservices.userservice.exception.UserNotFoundException;
import io.diersonzeni.microservices.userservice.repository.UserRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private static final String POSTAL_SERVICE_URL = "http://postal-service";

	protected UserRepository userRepository;
	
	@Autowired
    private WebClient.Builder webClientBuilder;
	
	@Autowired
    private CircuitBreakerFactory<?, ?> circuitBreakerFactory;
	
	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;

		logger.info("total de usuários cadastratos: [" + userRepository.countUsers() + "]");
	}

	@RequestMapping("/users/{email}")
	public Mono<User> byId(@PathVariable("email") String email) {

		logger.info("users-service byId() invoked: " + email);
		User user = userRepository.findByEmail(email);
		logger.info("users-service byId() found: " + user);

		if (user == null) {
			throw new UserNotFoundException(email);
		} else {
			return Mono.just(user);
		}
	}
	
	@PostMapping(path = "/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Void> create(@RequestBody User newUser) {
		
		User user = new User(User.getNextId(), newUser.getEmail(), newUser.getName(), newUser.getStreetId());

		boolean sucess = userRepository.addUser(user);
		if (sucess) {
			setAddress(user);
			return Mono.empty();
		} else {
			throw new GenericException("Erro ao cadastrar usuário");
		}
	}
	
	@GetMapping("/users")
    public Flux<User> getAll() {
		
		List<User> users = userRepository.getAll();

		for (User user : users) {
			setAddress(user);
		}
		
        return Flux.fromIterable(userRepository.getAll());
    }
	
	private void setAddress(User user) {
		logger.info("users-service setAddress() invoked");
		CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");

		Mono<String> street = circuitBreaker.run(() -> webClientBuilder.baseUrl(POSTAL_SERVICE_URL).build().get()
				.uri("street/{streetId}", user.getStreetId()).retrieve().bodyToMono(String.class));

		street.subscribe(a -> {
			try {
				JsonNode root = new JsonMapper().readTree(a);
				user.setStreet(root.path("name").asText());
				JsonNode city = root.path("city");
				user.setCity(city.path("name").asText());
				user.setState(city.path("state").path("name").asText());
			} catch (JsonProcessingException e) {
				logger.error("Erro ao processar JSON de retorno", e);
			}
		});
	}
}