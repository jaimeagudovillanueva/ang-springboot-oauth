package es.rest.hateoas;

import org.springframework.hateoas.ResourceSupport;

public class BaseResource extends ResourceSupport {

	private Object datos;

	public Object getDatos() {
		return datos;
	}

	public void setDatos(final Object datos) {
		this.datos = datos;
	}

}
