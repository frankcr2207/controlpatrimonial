package csjar.controlpatrimonial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import csjar.controlpatrimonial.entity.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Integer>{

	List<Perfil> findByModuloUsuarioIsNull();
	
}
