package csjar.controlpatrimonial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import csjar.controlpatrimonial.entity.BienVer;

public interface BienVerRepository extends JpaRepository<BienVer, Integer>{

	List<BienVer> findByIdBienIn(List<Integer> idBien);
	
}
