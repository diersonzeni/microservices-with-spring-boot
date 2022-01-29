package io.diersonzeni.microservices.postalservice.repository;

import java.util.Set;

import io.diersonzeni.microservices.postalservice.dto.City;
import io.diersonzeni.microservices.postalservice.dto.State;
import io.diersonzeni.microservices.postalservice.dto.Street;

public interface PostalServiceRepository {

	public Street findStreetById(Long streetId);
	
	public Set<Street> findStreetByCEP(Long cep);
	
	public Set<City> findCityByState(Long idState);
	
	public Set<State> findAllStates();
}
