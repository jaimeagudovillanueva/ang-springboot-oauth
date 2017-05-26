package es.rest.controller;

import java.security.Principal;
import java.util.Set;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.rest.entity.Permiso;
import es.rest.entity.Usuario;

@RestController
public class UserController {

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public Principal obtenerUsuario(@RequestParam(value = "ambito") final String codigoAmbito,
			@RequestParam(value = "perfil") final Long idPerfil, final Principal principal) {

		final boolean permisoValido = actualizarPermisoSeleccionado((OAuth2Authentication) principal, codigoAmbito,
				idPerfil);

		// TODO Controlar excepciones
		if (!permisoValido) {
			;
		}
		return principal;
	}

	/**
	 * Actualiza el Usuario del Principal teniendo en cuenta el permiso que se
	 * haya seleccionado
	 *
	 * @param authentication
	 * @param codigoAmbito
	 * @param idPerfil
	 * @return
	 */
	private boolean actualizarPermisoSeleccionado(final OAuth2Authentication authentication, final String codigoAmbito,
			final Long idPerfil) {

		boolean permisoEncontrado = false;
		final Set<Permiso> permisos = ((Usuario) authentication.getPrincipal()).getPermisos();
		Permiso permisoSeleccionado = null;
		for (final Permiso permiso : permisos) {
			if (codigoAmbito.equals(permiso.getAmbito().getCodigo()) && idPerfil == permiso.getPerfil().getId()) {
				permisoSeleccionado = permiso;
				permisoEncontrado = true;
				break;
			}
		}

		((Usuario) authentication.getPrincipal()).setPermisoSeleccionado(permisoSeleccionado);
		return permisoEncontrado;
	}
}