package es.rest.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entidad para la tabla USUARIOS
 *
 * @author jaime.agudo
 *
 */
@Entity
@Table(name = "TBLAFP_USUARIOS")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_USUARIO")
	private Long id;

	@Column(name = "COD_USUARIO")
	private String username;

	@Column(name = "NOMBRE")
	private String nombre;

	@Column(name = "DE_PRI_APELLIDO")
	private String primerApellido;

	@Column(name = "DE_SEG_APELLIDO")
	private String segundoApellido;

	@Column(name = "DE_NIF")
	private String nif;

	@Column(name = "LG_ACTIVO")
	private boolean activo;

	@OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
	private Set<Permiso> permisos;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
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

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(final boolean activo) {
		this.activo = activo;
	}

	public Set<Permiso> getPermisos() {
		return permisos;
	}

	public void setPermisos(final Set<Permiso> permisos) {
		this.permisos = permisos;
	}
}