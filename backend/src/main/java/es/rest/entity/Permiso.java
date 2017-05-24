package es.rest.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entidad para la tabla PERMISOS
 *
 * @author jaime.agudo
 *
 */
@Entity
@Table(name = "TBLAFP_PERMISOS")
public class Permiso implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_PERMISO")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "ID_PERFIL")
	private Perfil perfil;

	@ManyToOne
	@JoinColumn(name = "CODIGO_AMBITO")
	private Ambito ambito;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(final Perfil perfil) {
		this.perfil = perfil;
	}

	public Ambito getAmbito() {
		return ambito;
	}

	public void setAmbito(final Ambito ambito) {
		this.ambito = ambito;
	}
}