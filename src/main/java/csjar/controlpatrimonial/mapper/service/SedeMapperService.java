package csjar.controlpatrimonial.mapper.service;

import java.util.List;

import csjar.controlpatrimonial.domain.Sede;
import csjar.controlpatrimonial.dto.ResponseSedeDTO;

public interface SedeMapperService {

	List<ResponseSedeDTO> toDTO(List<Sede> sedes);
	
}
