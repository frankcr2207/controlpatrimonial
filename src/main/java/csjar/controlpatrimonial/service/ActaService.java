package csjar.controlpatrimonial.service;

import csjar.controlpatrimonial.dto.RequestActaDTO;

public interface ActaService {
	
	void guardarActa(RequestActaDTO requestActaDTO) throws Exception;
}
