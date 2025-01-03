package csjar.controlpatrimonial.mapper.service;

import java.util.List;

import csjar.controlpatrimonial.dto.ResponseSedeDTO;
import csjar.controlpatrimonial.entity.Sede;

public interface SedeMapperService {

	List<ResponseSedeDTO> toDTO(List<Sede> sedes);
	
}
