package csjar.controlpatrimonial.service;

import java.util.List;

import csjar.controlpatrimonial.entity.Bien;
import csjar.controlpatrimonial.entity.BienVer;

public interface BienVerService {

	void generarVersion(List<Bien> bienes, Integer idActa);
	List<BienVer> obtenerEntidades(Integer idBien);
	
}
