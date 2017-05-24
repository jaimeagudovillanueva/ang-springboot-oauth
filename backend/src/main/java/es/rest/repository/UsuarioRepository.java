package es.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.rest.entity.Usuario;

/**
 * Repositorio para la entidad Usuario
 *
 * @author jaime.agudo
 *
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findOneByUsername(String username);

}
