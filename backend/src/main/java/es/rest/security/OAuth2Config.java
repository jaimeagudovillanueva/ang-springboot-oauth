package es.rest.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import es.rest.repository.UsuarioRepository;
import es.rest.security.properties.OauthProperties;

@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private OauthProperties oauthProperties;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public void configure(final AuthorizationServerSecurityConfigurer configurer) throws Exception {
		configurer.checkTokenAccess("isAutenticated()");
	}

	@Override
	public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenEnhancer(tokenEnhancer());
		endpoints.authenticationManager(authenticationManager);
	}

	@Override
	public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient(oauthProperties.getClientid()).authorizedGrantTypes("password", "refresh_token")
				.scopes("read", "write").accessTokenValiditySeconds(3600).secret(oauthProperties.getSecret());
	}

	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
	}

	/**
	 * Creamos un token enhacer para incluir la información del usuario en la
	 * respuesta del token, ya que necesitamos saber los permisos que tiene el
	 * usuario
	 *
	 * @author jaime.agudo
	 *
	 */
	public class CustomTokenEnhancer implements TokenEnhancer {
		@Override
		public OAuth2AccessToken enhance(final OAuth2AccessToken accessToken,
				final OAuth2Authentication authentication) {

			final Map<String, Object> additionalInfo = new HashMap<>();
			additionalInfo.put("user", usuarioRepository.findOneByUsername(authentication.getName()));

			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
			return accessToken;
		}
	}
}