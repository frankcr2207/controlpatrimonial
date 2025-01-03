package csjar.controlpatrimonial.service;

import java.util.List;

import csjar.controlpatrimonial.dto.ResponseTipoAdquisicionDTO;
import csjar.controlpatrimonial.entity.TipoAdquisicion;

public interface TipoAdquisicionService {

	TipoAdquisicion obtenerEntidad(Integer id);
	
	List<ResponseTipoAdquisicionDTO> listarTiposAdquisicion();
	
}
