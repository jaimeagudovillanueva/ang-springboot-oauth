package es.rest.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import es.rest.entity.Ambito;
import es.rest.entity.Perfil;
import es.rest.repository.AmbitoRepository;
import es.rest.repository.PerfilRepository;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	public static final String ROLE_PREFIX = "ROLE_";

	@Autowired
	PerfilRepository perfilRepository;

	@Autowired
	AmbitoRepository ambitoRepository;

	@Override
	public void configure(final HttpSecurity http) throws Exception {
		http.cors().and().authorizeRequests().antMatchers("/").permitAll().antMatchers("/personas/**")
				.hasRole("SOLICITANTE").antMatchers("/user").hasRole("ARCHIVERO")
				.accessDecisionManager(defaultOauthDecisionManager(roleHierarchy()));
	}

	@Bean
	public RoleHierarchy roleHierarchy() {
		final StringBuilder roleHierachy = new StringBuilder();

		final List<Perfil> perfiles = perfilRepository.findAll();
		final List<Ambito> ambitos = ambitoRepository.findAll();

		for (final Perfil perfil : perfiles) {
			final String rolPadre = ROLE_PREFIX + perfil.getDescripcion().toUpperCase();
			for (final Ambito ambito : ambitos) {
				final String roleMapping = rolPadre + "_" + ambito.getCodigo() + " > " + rolPadre;
				roleHierachy.append(roleMapping);
				roleHierachy.append(System.getProperty("line.separator"));
			}
		}

		final RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		roleHierarchy.setHierarchy(roleHierachy.toString());
		return roleHierarchy;
	}

	@Bean
	public RoleHierarchyVoter roleVoter() {
		return new RoleHierarchyVoter(roleHierarchy());
	}

	@Bean
	public AffirmativeBased defaultOauthDecisionManager(final RoleHierarchy roleHierarchy) {

		final List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();

		final OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
		expressionHandler.setRoleHierarchy(roleHierarchy);
		final WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
		webExpressionVoter.setExpressionHandler(expressionHandler);
		decisionVoters.add(webExpressionVoter);
		decisionVoters.add(roleVoter());
		return new AffirmativeBased(decisionVoters);
	}
}