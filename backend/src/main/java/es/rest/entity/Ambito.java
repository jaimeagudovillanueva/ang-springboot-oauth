package es.rest.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad para la tabla AMBITO
 *
 * @author jaime.agudo
 *
 */
@Entity
@Table(name = "TBMAFP_AMBITO")
public class Ambito implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO")
	private String codigo;

	@Column(name = "DESCRIPCION")
	private String descripcion;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(final String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}
}