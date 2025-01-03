package csjar.controlpatrimonial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import csjar.controlpatrimonial.entity.Modelo;

public interface ModeloRepository extends JpaRepository<Modelo, Integer>{

	List<Modelo> findByIdIn(List<Integer> ids);
	
}
