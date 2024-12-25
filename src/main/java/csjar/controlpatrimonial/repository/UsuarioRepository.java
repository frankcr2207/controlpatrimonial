package csjar.controlpatrimonial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import csjar.controlpatrimonial.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

	Usuario findByLoginAndEstadoAndPerfilIsNotNull(String login, String estado);
	
	Usuario findByDni(String dni);
	
	List<Usuario> findByPerfilIsNotNull();
	
}
