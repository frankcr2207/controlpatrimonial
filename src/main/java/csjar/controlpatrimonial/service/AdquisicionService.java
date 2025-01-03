package csjar.controlpatrimonial.service;

import java.util.List;

import csjar.controlpatrimonial.dto.RequestAdquisicionDTO;
import csjar.controlpatrimonial.dto.ResponseAdquisicionDTO;
import csjar.controlpatrimonial.entity.Adquisicion;

public interface AdquisicionService {
	
	ResponseAdquisicionDTO verAdquisicion(Integer id);
	List<ResponseAdquisicionDTO> buscarAdquisicion(String documento);
	void guardarAdquisicion(RequestAdquisicionDTO requestAdquisicionDTO);
	Adquisicion obtenerEntidad(Integer id);
	void actualizarEntidad(Adquisicion adquisicion);
}
