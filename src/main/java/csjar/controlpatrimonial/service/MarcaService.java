package csjar.controlpatrimonial.service;

import java.util.List;

import csjar.controlpatrimonial.dto.ResponseMarcaDTO;
import csjar.controlpatrimonial.entity.Marca;

public interface MarcaService {

	List<ResponseMarcaDTO> listarMarcas(); 
	List<Marca> obtenerEntidades(List<Integer> ids);
	
}
