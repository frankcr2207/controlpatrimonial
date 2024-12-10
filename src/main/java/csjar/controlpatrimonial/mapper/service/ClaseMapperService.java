package csjar.controlpatrimonial.mapper.service;

import java.util.List;

import csjar.controlpatrimonial.domain.Clase;
import csjar.controlpatrimonial.dto.ResponseClaseDTO;

public interface ClaseMapperService {

	List<ResponseClaseDTO> toDTO(List<Clase> clases);
	
}
