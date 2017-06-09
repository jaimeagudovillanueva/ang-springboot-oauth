package es.rest.hateoas;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import es.rest.controller.PersonaRestController;
import es.rest.entity.Persona;

/**
 * Recurso HATEHOAS para la entidad Persona
 *
 * @author jaime.agudo
 *
 */
public class PersonaResource extends BaseResource {


	public PersonaResource(final Persona persona) {
		setDatos(persona);

		this.add(linkTo(methodOn(PersonaRestController.class).obtenerPersonas(null)).withRel("personas"));

		this.add(linkTo(PersonaRestController.class).slash(persona.getId()).withSelfRel());
		this.add(linkTo(PersonaRestController.class).slash(persona.getId()).slash("nifoculto")
				.withRel("persona_sin_nif"));
	}
}
