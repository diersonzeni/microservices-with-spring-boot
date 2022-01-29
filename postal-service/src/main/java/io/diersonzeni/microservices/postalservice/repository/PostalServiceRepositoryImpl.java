package io.diersonzeni.microservices.postalservice.repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import io.diersonzeni.microservices.postalservice.dto.City;
import io.diersonzeni.microservices.postalservice.dto.State;
import io.diersonzeni.microservices.postalservice.dto.Street;
import io.diersonzeni.microservices.postalservice.exception.StreetNotFoundException;

@Repository
public class PostalServiceRepositoryImpl implements PostalServiceRepository {

	private static Set<State> states = new HashSet<>();
	private static Set<City> citys = new HashSet<>();
	private static Set<Street> streets = new HashSet<>();
	
	{
		states.add(new State(1L, "Rio Grande do Sul"));
		states.add(new State(2L, "Santa Catarina"));
		states.add(new State(3L, "Paraná"));
		states.add(new State(4L, "São Paulo"));
		states.add(new State(5L, "Mato Grosso do Sul"));
		states.add(new State(6L, "Rio de Janeiro"));
		states.add(new State(7L, "Minas Gerais"));
	}
	
	{
		final State[] arrStates = states.toArray(new State[states.size()]);
		
		citys.add(new City(1L, "Porto Alegre", arrStates[0]));
		citys.add(new City(2L, "Caxias do Sul", arrStates[0]));
		citys.add(new City(3L, "Florianópolis", arrStates[1]));
		citys.add(new City(4L, "Joinvile", arrStates[1]));
		citys.add(new City(5L, "Curitiba", arrStates[2]));
		citys.add(new City(6L, "Paranaguá", arrStates[2]));
		citys.add(new City(7L, "São Paulo", arrStates[3]));
		citys.add(new City(8L, "Santos", arrStates[3]));
		citys.add(new City(9L, "Campo Grande", arrStates[4]));
		citys.add(new City(10L, "Rio de Janeiro", arrStates[5]));
		citys.add(new City(11L, "Belo Horizonte", arrStates[6]));
	}
	
	{
		final City[] arrCitys = citys.toArray(new City[citys.size()]);
		
		streets.add(new Street(1L, "Rua Marechal Deodoro", 80020320L, arrCitys[4]));
		streets.add(new Street(2L, "Avenida Paulista", 1310913L, arrCitys[6]));
	}
	
	@Override
	public Street findStreetById(Long streetId) {
		
		Optional<Street> street = streets.stream()
		        .filter(s -> s.getId().equals(streetId))
		        .collect(Collectors.reducing((a, b) -> null));
		
		if (street.isPresent()) {
			return street.get();
		} else {
			throw new StreetNotFoundException(streetId);
		}
	}
	
	@Override
	public Set<Street> findStreetByCEP(Long cep) {
		return streets.stream().filter(s -> s.getCep().equals(cep)).collect(Collectors.toSet());
	}

	@Override
	public Set<City> findCityByState(Long idState) {
		return citys.stream().filter(c -> c.getState().getId().equals(idState)).collect(Collectors.toSet());
	}

	@Override
	public Set<State> findAllStates() {
		return states;
	}


}
