package io.diersonzeni.microservices.postalservice.controller;

import java.util.Set;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.diersonzeni.microservices.postalservice.dto.City;
import io.diersonzeni.microservices.postalservice.dto.State;
import io.diersonzeni.microservices.postalservice.dto.Street;
import io.diersonzeni.microservices.postalservice.repository.PostalServiceRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class PostalServiceController {
	
	protected Logger logger = Logger.getLogger(PostalServiceController.class.getName());
	
	protected PostalServiceRepository postalServiceRepository;

	@Autowired
	public PostalServiceController(PostalServiceRepository postalServiceRepository) {
		this.postalServiceRepository = postalServiceRepository;
	}

	@GetMapping("/state")
	public Flux<State> findAllStates() {

		logger.info("postal-service findAllStates() invoked");
		Set<State> states = postalServiceRepository.findAllStates();

		return Flux.fromIterable(states);
	}
	
	@GetMapping("/state/{stateId}/city")
	public Flux<City> findCityByStateId(@PathVariable("stateId") Long stateId) {

		logger.info("postal-service findCityByStateId() invoked");
		Set<City> citys = postalServiceRepository.findCityByState(stateId);

		return Flux.fromIterable(citys);
	}
	
	@GetMapping("/street/cep/{cep}")
	public Flux<Street> findStreetByCEP(@PathVariable("cep") Long cep) {

		logger.info("postal-service findStreetByCEP() invoked");
		Set<Street> streets = postalServiceRepository.findStreetByCEP(cep);

		return Flux.fromIterable(streets);
	}
	
	@GetMapping("/street/{streetId}")
	public Mono<Street> findStreetByStreetId(@PathVariable("streetId") Long streetId) {

		logger.info("postal-service findStreetByStreetId() invoked");
		Street street = postalServiceRepository.findStreetById(streetId);

		return Mono.justOrEmpty(street);
	}
}
