package es.rest.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("ldap")
public class LdapProperties {

	private String url;
	private String user;
	private String password;
	private String base;
	private String authfilter;
	private String searchbase;

	public String getUrl() {
		return url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(final String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getBase() {
		return base;
	}

	public void setBase(final String base) {
		this.base = base;
	}

	public String getAuthfilter() {
		return authfilter;
	}

	public void setAuthfilter(final String authfilter) {
		this.authfilter = authfilter;
	}

	public String getSearchbase() {
		return searchbase;
	}

	public void setSearchbase(final String searchbase) {
		this.searchbase = searchbase;
	}
}