package es.rest.security.mapper;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.stereotype.Component;

/**
 * Necesitamos definir un mapeo específico del detalle del usuario ya que la
 * autenticación es por LDAP pero los roles los tenemos en base de datos
 *
 * @author jaime.agudo
 *
 */
@Component
public class UsuarioMapper extends LdapUserDetailsMapper implements UserDetailsContextMapper {

	@Autowired
	@Qualifier("usuarioService")
	private UserDetailsService userService;

	@Override
	public UserDetails mapUserFromContext(final DirContextOperations ctx, final String username,
			final Collection<? extends GrantedAuthority> authorities) {

		return userService.loadUserByUsername(username);
	}
}
