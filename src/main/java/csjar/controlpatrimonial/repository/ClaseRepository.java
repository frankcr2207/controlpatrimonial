package csjar.controlpatrimonial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import csjar.controlpatrimonial.entity.Clase;

public interface ClaseRepository extends JpaRepository<Clase, Integer>{
	
	List<Clase> findByIdIn(List<Integer> ids);

}
