package es.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.rest.entity.Ambito;

/**
 * Repositorio para la entidad Ambito
 *
 * @author jaime.agudo
 *
 */
public interface AmbitoRepository extends JpaRepository<Ambito, Long> {

	@Override
	List<Ambito> findAll();
}
