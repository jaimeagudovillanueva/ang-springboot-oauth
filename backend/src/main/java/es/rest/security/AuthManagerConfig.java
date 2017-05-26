package es.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

import es.rest.security.mapper.UsuarioMapper;
import es.rest.security.properties.LdapProperties;

/**
 * Se configura el authenticationManager para que autentique contra LDAP. Los
 * parámetros de conexión al LDAP se obtienen de un properties y los detalles
 * del usuario una vez autenticado se asignan concustomUserDetailMapper que
 * ataca a base de datos
 *
 * @author jaime.agudo
 *
 */
@Configuration
public class AuthManagerConfig extends GlobalAuthenticationConfigurerAdapter {

	@Autowired
	private LdapProperties ldapProperties;

	@Autowired
	private UsuarioMapper customUserDetailMapper;

	@Autowired
	public void authenticationManager(final AuthenticationManagerBuilder builder) throws Exception {

		final DefaultSpringSecurityContextSource contextSource = new DefaultSpringSecurityContextSource(
				ldapProperties.getUrl());
		contextSource.setUserDn(ldapProperties.getUser());
		contextSource.setPassword(ldapProperties.getPassword());
		contextSource.setReferral("follow");
		contextSource.setBase(ldapProperties.getBase());
		contextSource.afterPropertiesSet();

		final LdapAuthenticationProviderConfigurer<AuthenticationManagerBuilder> ldapAuthenticationProviderConfigurer = builder
				.ldapAuthentication();

		ldapAuthenticationProviderConfigurer.userSearchFilter(ldapProperties.getAuthfilter())
				.userSearchBase(ldapProperties.getSearchbase()).contextSource(contextSource);
		ldapAuthenticationProviderConfigurer.userDetailsContextMapper(customUserDetailMapper);
	}
}
