package es.rest.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import es.rest.ServicesApplication;
import es.rest.entity.Persona;

@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class)
@SpringBootTest(classes = ServicesApplication.class)
public class PersonaRepositoryTest {

	@Resource
	PersonaRepository personaRepository;

	Pageable pageable;

	@Before
	public void setup() throws Exception {
		pageable = new PageRequest(1, 20, new Sort("primerApellido"));
	}

	@Test
	public void testPersonalFindAll() {
		final List<Persona> personas = personaRepository.findAll(pageable).getContent();
		assertEquals(personas.size(), 20);
	}

	@Test
	public void testPersonaFindOne() {
		final Persona persona = personaRepository.findOne(195L);
		assertEquals(persona.getNif(), "00716189");
	}

	@Test
	public void testFindPersonaNombreApellido() {
		final List<Persona> personas = personaRepository
				.findByNombreAndPrimerApellidoOrderByPrimerApellidoAsc("ARACELI", "GARCIA");
		assertEquals(personas.get(0).getNif(), "00716189");
	}

	@Test
	public void testFindLikeNombreApellido() {
		final List<Persona> personas = personaRepository.findLikeNombreApellido("GARC");
		assertEquals(personas.size(), 7941);
	}

	@Test
	public void testFindAllFiltro() {
		final List<Persona> personas = personaRepository.findAll(PersonaRepository.cumpleFiltro("GARC"));
		assertEquals(personas.size(), 15372);
	}

	@Test
	public void testFindAllIdMenor() {
		final List<Persona> personas = personaRepository.findAll(PersonaRepository.idMenor(200L));
		assertEquals(personas.size(), 197);
	}

	@Test
	public void testFindAllDoubleSpecification() {
		final List<Persona> personas = personaRepository.findAll(
				Specifications.where(PersonaRepository.idMenor(200L)).and(PersonaRepository.cumpleFiltro("GARC")));
		assertEquals(personas.size(), 12);
	}
}