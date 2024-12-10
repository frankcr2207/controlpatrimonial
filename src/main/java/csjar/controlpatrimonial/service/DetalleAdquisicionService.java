package csjar.controlpatrimonial.service;

import java.util.List;

import csjar.controlpatrimonial.domain.DetalleAdquisicion;

public interface DetalleAdquisicionService {

	List<DetalleAdquisicion> obtenerEntidades(List<Integer> ids);
	
}
