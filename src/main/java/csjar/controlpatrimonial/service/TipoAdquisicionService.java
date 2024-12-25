package csjar.controlpatrimonial.service;

import java.util.List;

import csjar.controlpatrimonial.domain.TipoAdquisicion;
import csjar.controlpatrimonial.dto.ResponseTipoAdquisicionDTO;

public interface TipoAdquisicionService {

	TipoAdquisicion obtenerEntidad(Integer id);
	
	List<ResponseTipoAdquisicionDTO> listarTiposAdquisicion();
	
}
