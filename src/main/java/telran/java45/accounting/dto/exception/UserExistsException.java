package telran.java45.accounting.dto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserExistsException extends RuntimeException {

	private static final long serialVersionUID = 7412542832469172342L;

	public UserExistsException(String login) {
		super("User " + login + " exists");
	}
}
