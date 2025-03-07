package csjar.controlpatrimonial.service;

import java.util.List;

import csjar.controlpatrimonial.dto.ResponseSedeDTO;
import csjar.controlpatrimonial.entity.Sede;

public interface SedeService {

	Sede obtenerEntidad(Integer id);
	List<ResponseSedeDTO> obtenerSedes();
	
}
