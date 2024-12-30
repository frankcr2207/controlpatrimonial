package csjar.controlpatrimonial.service;

import java.io.OutputStream;
import java.util.Map;

import csjar.controlpatrimonial.dto.RequestActaDTO;

public interface ActaService {
	
	void guardarActa(RequestActaDTO requestActaDTO);
	void generarActa(OutputStream outStream, String templateName, Map<String, Object> data);

}
