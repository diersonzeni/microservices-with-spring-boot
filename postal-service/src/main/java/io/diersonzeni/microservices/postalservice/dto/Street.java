package io.diersonzeni.microservices.postalservice.dto;

import java.io.Serializable;

public class Street implements Serializable{

	private static final long serialVersionUID = -746178632388051897L;

	private Long id;
	
	private String name;

	private Long cep;
	
	private City city;
	
	public Street() {
		super();
	}

	public Street(Long id, String name, Long cep, City city) {
		super();
		this.id = id;
		this.name = name;
		this.cep = cep;
		this.city = city;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Street other = (Street) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Long getCep() {
		return cep;
	}

	public void setCep(Long cep) {
		this.cep = cep;
	}
}
