package io.diersonzeni.microservices.postalservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StreetNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 5018317422721953913L;

	public StreetNotFoundException(Long streetId) {
		super("Rua não encontrada: [" + streetId + "]");
	}
}
