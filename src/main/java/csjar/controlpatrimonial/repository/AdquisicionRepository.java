package csjar.controlpatrimonial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import csjar.controlpatrimonial.entity.Adquisicion;

public interface AdquisicionRepository extends JpaRepository<Adquisicion, Integer>{
	
	List<Adquisicion> findByDocumentoContains(String documento);

}
