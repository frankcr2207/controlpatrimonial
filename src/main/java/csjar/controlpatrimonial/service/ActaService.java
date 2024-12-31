package csjar.controlpatrimonial.service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import csjar.controlpatrimonial.dto.RequestActaDTO;

public interface ActaService {
	
	void guardarActa(RequestActaDTO requestActaDTO);
	void generarActa(OutputStream outStream, String templateName, Map<String, Object> data);
	void convertExcelToPdf(ByteArrayOutputStream excelFile, HttpServletResponse response) throws Exception;
}
