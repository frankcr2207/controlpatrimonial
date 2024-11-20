package csjar.controlpatrimonial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import csjar.controlpatrimonial.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

	Usuario findByDni(String dni);
	
}
