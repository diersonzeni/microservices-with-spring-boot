package io.diersonzeni.microservices.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -6291227897284123366L;

	public UserNotFoundException(String userId) {
		super("Usuário não encontrado: [" + userId + "]");
	}
}
