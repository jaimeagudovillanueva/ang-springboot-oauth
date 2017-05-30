package es.rest.exception;

public class NotFoundException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = -713186692452374616L;

	public NotFoundException(final String mensaje) {
		super(mensaje);
    }
}
