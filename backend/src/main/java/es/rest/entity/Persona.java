package es.rest.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entidad para la tabla PERSONA
 *
 * @author jaime.agudo
 *
 */
@Entity
@Table(name = "TBMAFP_PERSONA")
@SequenceGenerator(name = "TBMAFP_PERSONA_ID_SEQGEN", sequenceName = "SEC_PERSONA", allocationSize = 1)
public class Persona implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "TBMAFP_PERSONA_ID_SEQGEN", strategy = GenerationType.SEQUENCE)
	@Column(name = "ID_PERSONA")
	private Long id;

	@Column(name = "DE_NOMBRE")
	private String nombre;

	@Column(name = "DE_PRIMER_APELLIDO")
	private String primerApellido;

	@Column(name = "DE_SEGUNDO_APELLIDO")
	private String segundoApellido;

	@Column(name = "NU_NIF")
	private String nif;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

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