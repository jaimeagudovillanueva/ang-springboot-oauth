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
import es.rest.exception.NotFoundException;

@RestController
public class UserController {

	@RequestMapping(value = "/seleccionarPermiso", method = RequestMethod.GET)
	public Permiso obtenerUsuario(@RequestParam(value = "ambito") final String codigoAmbito,
			@RequestParam(value = "perfil") final Long idPerfil, final Principal principal) throws NotFoundException {

		final Permiso permiso = actualizarPermisoSeleccionado((OAuth2Authentication) principal, codigoAmbito,
				idPerfil);

		if (permiso == null) {
			throw new NotFoundException("El usuario no tiene el permiso seleccionado.");
		}
		return permiso;
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
	private Permiso actualizarPermisoSeleccionado(final OAuth2Authentication authentication, final String codigoAmbito,
			final Long idPerfil) {

		final Set<Permiso> permisos = ((Usuario) authentication.getPrincipal()).getPermisos();
		final Permiso permisoSeleccionado = permisos.stream()
				.filter(permiso -> codigoAmbito.equals(permiso.getAmbito().getCodigo())
						&& idPerfil == permiso.getPerfil().getId())
				.findFirst().orElse(null);

		((Usuario) authentication.getPrincipal()).setPermisoSeleccionado(permisoSeleccionado);
		return permisoSeleccionado;
	}
}