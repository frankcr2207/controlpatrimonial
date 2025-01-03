package csjar.controlpatrimonial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import csjar.controlpatrimonial.entity.Acta;

public interface ActaRepository extends JpaRepository<Acta, Integer>{

	 @Query("SELECT a FROM Acta a WHERE a.numero = (SELECT MAX(b.numero) FROM Acta b WHERE YEAR(b.fecRegistro) = :year) AND YEAR(a.fecRegistro) = :year")
	 Acta findActaWithMaxNumeroByYear(@Param("year") Integer year);
	 
}
