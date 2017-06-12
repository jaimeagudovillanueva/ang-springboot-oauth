package es.rest.controller.json;

public class FiltroPersona extends ListParams {

	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private String nif;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(final String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(final String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(final String nif) {
		this.nif = nif;
	}
}