package es.rest.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.util.StringUtils;

import es.rest.controller.json.FiltroPersona;
import es.rest.entity.Persona;

/**
 * Repositorio para la entidad Persona
 *
 * @author jaime.agudo
 *
 */
public interface PersonaRepository
		extends PagingAndSortingRepository<Persona, Long>, JpaSpecificationExecutor<Persona> {

	@Override
	Persona findOne(Long id);

	@Override
	Page<Persona> findAll(Pageable pageable);

	@SuppressWarnings("unchecked")
	@Override
	Persona save(Persona persona);

	List<Persona> findByNombreAndPrimerApellidoOrderByPrimerApellidoAsc(String nombre, String primerApellido);

	@Query("from Persona where nombre like %?1% or primerApellido like %?1% order by primerApellido ASC")
	List<Persona> findLikeNombreApellido(String texto);

	public static Specification<Persona> cumpleFiltro(final FiltroPersona filtro) {
		return new Specification<Persona>() {
			@Override
			public Predicate toPredicate(final Root<Persona> root, final CriteriaQuery<?> query,
					final CriteriaBuilder builder) {

				final List<Predicate> predicates = new ArrayList<>();
				if (!StringUtils.isEmpty(filtro.getNif())) {
					predicates.add(builder.equal(builder.upper(root.get("nif")), filtro.getNif().toUpperCase()));
				}
				if (!StringUtils.isEmpty(filtro.getNombre())) {
					predicates.add(builder.like(builder.upper(root.get("nombre")),
							'%' + filtro.getNombre().toUpperCase() + '%'));
				}
				if (!StringUtils.isEmpty(filtro.getPrimerApellido())) {
					predicates.add(builder.like(builder.upper(root.get("primerApellido")),
							'%' + filtro.getPrimerApellido().toUpperCase() + '%'));
				}
				if (!StringUtils.isEmpty(filtro.getSegundoApellido())) {
					predicates.add(builder.like(builder.upper(root.get("segundoApellido")),
							'%' + filtro.getSegundoApellido().toUpperCase() + '%'));
				}

				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}

	public static Specification<Persona> idMenor(final Long id) {
		return new Specification<Persona>() {
			@Override
			public Predicate toPredicate(final Root<Persona> root, final CriteriaQuery<?> query,
					final CriteriaBuilder builder) {
				return builder.lessThan(root.get("id"), id);
			}
		};
	}

}
