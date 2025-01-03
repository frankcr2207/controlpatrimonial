package csjar.controlpatrimonial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import csjar.controlpatrimonial.entity.GrupoClase;

public interface GrupoClaseRepository extends JpaRepository<GrupoClase, Integer>{

	GrupoClase findByIdGrupoAndIdClase(Integer idGrupo, Integer idClase);
	
}
