package csjar.controlpatrimonial.service;

import java.util.List;

import csjar.controlpatrimonial.entity.Area;

public interface AreaService {

	Area obtenerEntidad(Integer id);
	
	List<Area> obtenerEntidades(List<Integer> idSedeOrgano);
	
}
