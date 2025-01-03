package csjar.controlpatrimonial.mapper.service;

import java.util.List;

import csjar.controlpatrimonial.dto.ResponseClaseDTO;
import csjar.controlpatrimonial.entity.Clase;

public interface ClaseMapperService {

	List<ResponseClaseDTO> toDTO(List<Clase> clases);
	
}
