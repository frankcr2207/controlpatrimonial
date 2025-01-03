package csjar.controlpatrimonial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import csjar.controlpatrimonial.entity.Bien;

public interface BienRepository extends JpaRepository<Bien, Integer>{

	List<Bien> findByIdAdquisicion(Integer idAdqiusicion);
	Bien findByCodigoPatrimonial(String codigo);
	
}
