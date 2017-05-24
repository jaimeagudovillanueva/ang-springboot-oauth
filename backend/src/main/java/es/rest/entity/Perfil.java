package es.rest.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad para la tabla PERFIL
 *
 * @author jaime.agudo
 *
 */
@Entity
@Table(name = "TBMAFP_PERFIL")
public class Perfil implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_PERFIL")
	private Long id;

	@Column(name = "DESCRIPCION")
	private String descripcion;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}
}