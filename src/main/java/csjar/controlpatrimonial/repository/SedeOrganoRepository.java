package csjar.controlpatrimonial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import csjar.controlpatrimonial.entity.SedeOrgano;

public interface SedeOrganoRepository extends JpaRepository<SedeOrgano, Integer>{

	List<SedeOrgano> findByIdSedeIn(Integer idSede);
	List<SedeOrgano> findByIdSedeAndIdOrganoIn(Integer idSede, List<Integer> idOrgano);
	
}
