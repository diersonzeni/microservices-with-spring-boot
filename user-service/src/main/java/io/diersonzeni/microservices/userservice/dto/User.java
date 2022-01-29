package io.diersonzeni.microservices.userservice.dto;

import java.io.Serializable;
import java.util.regex.Pattern;

import io.diersonzeni.microservices.userservice.exception.InvalidEmailException;

public class User implements Serializable {

	private static final long serialVersionUID = 7781522156609547240L;
	
	private static final String EMAIL_PATTERN_VALIDATION = "^(.+)@(\\S+)$";

	private static Long nextId = 0L;

	protected Long id;
	
	protected String email;

	protected String name;
	
	protected Long streetId;
	
	protected String street;
	
	protected String city;
	
	protected String state;
	
	protected User() {
		
	}
	
	public User(Long id, String email, String name, Long streetId) {
		super();
		if(!isValidEmail(email)) {
			throw new InvalidEmailException(email);
		};

		if(id == null) {
			this.id = getNextId();
		}else {
			this.id = id;
		}
		
		this.email = email;
		this.name = name;
		this.streetId = streetId;
	}
	
	private boolean isValidEmail(String email) {
		return Pattern.compile(EMAIL_PATTERN_VALIDATION).matcher(email).matches();
	}

	public static Long getNextId() {
		synchronized (nextId) {
			return nextId++;
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	

	public String toString() {
		return "id: [" + id + "], email: [" + email + "], name: [" + name + "]";
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getStreetId() {
		return streetId;
	}

	public void setStreetId(Long streetId) {
		this.streetId = streetId;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	

}
