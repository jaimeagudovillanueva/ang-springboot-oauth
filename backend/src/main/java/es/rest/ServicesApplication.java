package es.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Clase principal
 *
 * @author jaime.agudo
 *
 */
@SpringBootApplication
public class ServicesApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		return application.sources(ServicesApplication.class);
	}

	public static void main(final String[] args) {
		SpringApplication.run(ServicesApplication.class, args);
	}
}