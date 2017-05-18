package es.rest.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("oauth")
public class OauthProperties {

	private String clientid;
	private String secret;

	public String getClientid() {
		return clientid;
	}

	public void setClientid(final String clientid) {
		this.clientid = clientid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(final String secret) {
		this.secret = secret;
	}
}
