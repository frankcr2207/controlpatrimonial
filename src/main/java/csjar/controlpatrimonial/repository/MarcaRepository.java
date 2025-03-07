package csjar.controlpatrimonial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import csjar.controlpatrimonial.entity.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Integer>{

	List<Marca> findByIdIn(List<Integer> ids);
	
}
