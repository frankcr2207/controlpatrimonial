package csjar.controlpatrimonial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import csjar.controlpatrimonial.entity.Organo;

public interface OrganoRepository extends JpaRepository<Organo, Integer>{
	
	List<Organo> findByIdIn(List<Integer> id);

}
