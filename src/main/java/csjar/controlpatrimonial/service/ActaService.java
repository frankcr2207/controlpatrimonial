package csjar.controlpatrimonial.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import csjar.controlpatrimonial.dto.RequestActaDTO;
import csjar.controlpatrimonial.dto.ResponseActaDTO;

public interface ActaService {
	
	ResponseActaDTO guardarActa(RequestActaDTO requestActaDTO) throws Exception;
	ResponseActaDTO validarActa(Integer code, String token) throws Exception;
	void guardarConfirmacion(Integer id, MultipartFile multipartFile) throws IOException;
	byte[] descargarActa(Integer id) throws Exception;
	
}
