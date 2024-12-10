package csjar.controlpatrimonial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import csjar.controlpatrimonial.domain.DetalleAdquisicion;

public interface DetalleAdquisicionRepository extends JpaRepository<DetalleAdquisicion, Integer>{

	List<DetalleAdquisicion> findByIdIn(List<Integer> ids);
	
}
