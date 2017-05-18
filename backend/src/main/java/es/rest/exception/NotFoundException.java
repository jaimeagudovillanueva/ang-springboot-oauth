package es.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Not Found")
public class NotFoundException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -713186692452374616L;

	public NotFoundException() {
    }
}
