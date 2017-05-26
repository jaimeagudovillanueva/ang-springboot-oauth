package es.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

import es.rest.security.properties.LdapProperties;

/**
 * Clase principal
 *
 * @author jaime.agudo
 *
 */
@SpringBootApplication
public class ServicesApplication extends SpringBootServletInitializer {

	@Autowired
	@Qualifier("usuarioService")
	private UserDetailsService userService;

	@Autowired
	private LdapProperties ldapProperties;

	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		return application.sources(ServicesApplication.class);
	}

	public static void main(final String[] args) {
		SpringApplication.run(ServicesApplication.class, args);
	}

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

		ldapAuthenticationProviderConfigurer.userSearchFilter("uid={0}").userSearchBase("")
				.contextSource(contextSource);
		ldapAuthenticationProviderConfigurer.userDetailsContextMapper(customDetailMapper());
	}

	@Bean
	public CustomUserDetailsMapper customDetailMapper() {
		return new CustomUserDetailsMapper();
	}

	/**
	 * Necesitamos definir un mapeo específico del detalle del usuario ya que la
	 * autenticación es por LDAP pero los roles los tenemos en base de datos
	 *
	 * @author jaime.agudo
	 *
	 */
	public class CustomUserDetailsMapper extends LdapUserDetailsMapper implements UserDetailsContextMapper {

		@Override
		public UserDetails mapUserFromContext(final DirContextOperations ctx, final String username,
				final Collection<? extends GrantedAuthority> authorities) {

			return userService.loadUserByUsername(username);
		}
	}
}