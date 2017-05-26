package es.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.rest.entity.Perfil;

/**
 * Repositorio para la entidad Perfil
 *
 * @author jaime.agudo
 *
 */
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

	@Override
	List<Perfil> findAll();
}
