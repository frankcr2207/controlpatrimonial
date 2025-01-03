package csjar.controlpatrimonial.service;

import csjar.controlpatrimonial.dto.RequestActaDTO;
import csjar.controlpatrimonial.dto.ResponseActaDTO;

public interface ActaService {
	
	void guardarActa(RequestActaDTO requestActaDTO) throws Exception;
	ResponseActaDTO validarActa(Integer code, String token) throws Exception;
	
}
