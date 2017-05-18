package es.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.ldap.LdapAuthenticationProviderConfigurer;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

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
	}
}