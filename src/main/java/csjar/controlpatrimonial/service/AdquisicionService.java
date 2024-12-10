package csjar.controlpatrimonial.service;

import java.util.List;

import csjar.controlpatrimonial.dto.RequestAdquisicionDTO;
import csjar.controlpatrimonial.dto.RequestBienesDTO;
import csjar.controlpatrimonial.dto.ResponseAdquisicionDTO;

public interface AdquisicionService {

	List<ResponseAdquisicionDTO> buscarAdquisicion(String documento);
	void guardarAdquisicion(RequestAdquisicionDTO requestAdquisicionDTO);
	void generarBienes(List<RequestBienesDTO> requestAdquisicionDTO);
	
}
