package es.rest.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import es.rest.repository.AmbitoRepository;
import es.rest.repository.PerfilRepository;
import es.rest.security.voter.ArchivoVoter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	PerfilRepository perfilRepository;

	@Autowired
	AmbitoRepository ambitoRepository;

	@Autowired
	ArchivoVoter archivoVoter;

	@Override
	public void configure(final HttpSecurity http) throws Exception {
		http.cors().and().authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/personas/**").hasRole("ARCHIVERO")
			.antMatchers("/seleccionarPermiso").authenticated()
			.accessDecisionManager(defaultOauthDecisionManager());
	}

	@Bean
	public UnanimousBased defaultOauthDecisionManager() {

		final List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList<>();

		final OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
		final WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
		webExpressionVoter.setExpressionHandler(expressionHandler);
		decisionVoters.add(archivoVoter);
		decisionVoters.add(webExpressionVoter);
		return new UnanimousBased(decisionVoters);
	}
}