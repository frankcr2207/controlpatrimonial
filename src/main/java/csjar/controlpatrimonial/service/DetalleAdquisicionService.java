package csjar.controlpatrimonial.service;

import java.util.List;

import csjar.controlpatrimonial.entity.DetalleAdquisicion;

public interface DetalleAdquisicionService {

	List<DetalleAdquisicion> obtenerEntidades(List<Integer> ids);
	
}
