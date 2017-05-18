package es.rest.hateoas;

import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import es.rest.entity.Persona;

@Component
public class PersonaResourceAssembler implements ResourceAssembler<Persona, PersonaResource> {

	@Override
	public PersonaResource toResource(final Persona entity) {

		return new PersonaResource(entity);
	}
}
