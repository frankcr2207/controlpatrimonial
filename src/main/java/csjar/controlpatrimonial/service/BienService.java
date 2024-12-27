package csjar.controlpatrimonial.service;

import java.util.List;

import csjar.controlpatrimonial.dto.RequestBienesDTO;
import csjar.controlpatrimonial.dto.ResponseBienesDTO;

public interface BienService {

	List<ResponseBienesDTO> obtenerBienes(Integer idAdquisicion);
	void generarBienes(RequestBienesDTO requestBienesnDTO);
	
}
