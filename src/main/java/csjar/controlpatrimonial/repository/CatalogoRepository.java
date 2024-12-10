package csjar.controlpatrimonial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import csjar.controlpatrimonial.domain.Catalogo;

public interface CatalogoRepository extends JpaRepository<Catalogo, Integer>{

	List<Catalogo> findByIdIn(List<Integer> ids);
	
}
