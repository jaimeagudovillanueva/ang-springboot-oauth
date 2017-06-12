package es.rest.controller;

import static es.rest.repository.PersonaRepository.cumpleFiltro;
import static es.rest.repository.PersonaRepository.idMenor;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.rest.controller.json.FiltroPersona;
import es.rest.entity.Persona;
import es.rest.exception.NotFoundException;
import es.rest.hateoas.PersonaResource;
import es.rest.hateoas.PersonaResourceAssembler;
import es.rest.repository.PersonaRepository;

/**
 * Servicios REST con HATEHOAS
 *
 * @author jaime.agudo
 *
 */
@RestController
// Si ponemos el MediaType en la cabecera no hace falta ponerlo en cada método,
// ya que todos devuelven el mismo tipo
@RequestMapping(value = "/personas", produces = { MediaType.APPLICATION_JSON_VALUE, "application/hal+json" })
public class PersonaRestController extends ResourceSupport {

	@Resource
	PersonaRepository personaRepository;

	// Necesitamos los assembler para convertir de Page<Persona> a
	// PageResource<PersonaResource> para añadir a la respuesta los links
	// Hatehoas
	@Autowired
	private PersonaResourceAssembler personaAssembler;

	@Autowired
	private PagedResourcesAssembler<Persona> pagedPersonaAssembler;

	@RequestMapping(method = RequestMethod.POST)
	public PagedResources<PersonaResource> obtenerPersonas(@RequestBody final FiltroPersona filtro) {

		// Hay que restar 1 al page porque las páginas de pageable empiezan en
		// la 0 pero en las vistas lo normal es que sea la 1
		final Pageable pageable = new PageRequest(filtro.getPage() - 1, filtro.getLimit(),
				Direction.fromStringOrNull(filtro.getOrderByType()),
				filtro.getOrderBy(), "id");

		final Page<Persona> paginas = personaRepository.findAll(Specifications.where(cumpleFiltro(filtro)), pageable);

		return pagedPersonaAssembler.toResource(paginas, personaAssembler);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{idPersona}")
	public PersonaResource obtenerPersona(@PathVariable final Long idPersona) throws NotFoundException {
		final Persona persona = personaRepository.findOne(idPersona);
		if (persona == null) {
			throw new NotFoundException("No se ha encontrado ninguna persona con id " + idPersona);
		}
		return new PersonaResource(persona);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/nifoculto")
	public Resources<PersonaResource> obtenerPersonasSinNif(
			@RequestParam(value = "page") final Optional<Integer> page) {
		final Pageable pageable = new PageRequest(page.isPresent() ? page.get() : 0, 20, new Sort("primerApellido"));
		return new Resources<>(personaRepository.findAll(pageable).getContent().stream().map(persona -> {
			persona.setNif("#########");
			return new PersonaResource(persona);
		}).collect(Collectors.toList()));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/personasFiltroGenerico")
	public Resources<PersonaResource> obtenerPersonasFiltroGenerico(
			@RequestParam(value = "apellido") final String apellido, @RequestParam(value = "id") final Long id) {

		return new Resources<>(personaRepository.findAll(Specifications.where(idMenor(id))).stream()
				.filter(persona -> persona.getNombre().length() < 5).map(PersonaResource::new)
				.collect(Collectors.toList()));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{idPersona}/nifoculto")
	public PersonaResource obtenerPersonaSinNif(@PathVariable final Long idPersona) throws NotFoundException {
		final Persona persona = personaRepository.findOne(idPersona);
		if (persona != null) {
			persona.setNif("#########");
		} else {
			throw new NotFoundException("No se ha encontrado ninguna persona con id " + idPersona);
		}
		return new PersonaResource(persona);
	}
}
