package io.diersonzeni.microservices.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidEmailException extends RuntimeException {

	private static final long serialVersionUID = -5361599386884308160L;

	public InvalidEmailException(String email) {
		super("Email inválido: [" + email + "]");
	}
}
