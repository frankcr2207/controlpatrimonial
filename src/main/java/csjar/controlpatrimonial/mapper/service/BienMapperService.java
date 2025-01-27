package csjar.controlpatrimonial.mapper.service;

import csjar.controlpatrimonial.dto.ResponseBienDTO;
import csjar.controlpatrimonial.entity.Bien;

public interface BienMapperService {

	ResponseBienDTO toDTO(Bien bien);
	
}
