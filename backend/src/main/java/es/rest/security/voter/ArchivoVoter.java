package es.rest.security.voter;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import es.rest.entity.Permiso;
import es.rest.entity.Usuario;

/**
 * Voter que solo tiene en cuenta el permiso seleccionado por el usuario
 *
 * @author jaime.agudo
 *
 */
@Component
public class ArchivoVoter implements AccessDecisionVoter<Object> {

	public static final String ROLE_PREFIX = "ROLE_";

	@Override
	public int vote(final Authentication authentication, final Object object,
			final Collection<ConfigAttribute> attributes) {

		if (authentication == null) {
			return ACCESS_DENIED;
		}

		int result = ACCESS_ABSTAIN;
		if (authentication.getPrincipal() != null && authentication.getPrincipal() instanceof Usuario) {
			final Permiso permiso = ((Usuario) authentication.getPrincipal()).getPermisoSeleccionado();
			for (final ConfigAttribute attribute : attributes) {
				if (this.supports(attribute)) {
					result = ACCESS_DENIED;

					if (permiso != null && attribute.toString().contains(permiso.getAuthority())) {
						return ACCESS_GRANTED;
					}
				}
			}
		}

		return result;
	}

	/**
	 * En este voter solo se van a validar las peticiones que requieran un rol.
	 * Éstas se distingue porque contienen el prefilo de los roles
	 *
	 * @param attribute
	 * @return
	 *
	 */
	@Override
	public boolean supports(final ConfigAttribute attribute) {
		return attribute != null && attribute.toString().contains(ROLE_PREFIX);
	}

	@Override
	public boolean supports(final Class<?> clazz) {
		return true;
	}
}