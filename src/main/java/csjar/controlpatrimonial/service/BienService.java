package csjar.controlpatrimonial.service;

import java.io.IOException;
import java.util.List;

import com.google.zxing.WriterException;
import com.itextpdf.text.DocumentException;

import csjar.controlpatrimonial.dto.RequestBienesDTO;
import csjar.controlpatrimonial.dto.RequestEtiquetaDTO;
import csjar.controlpatrimonial.dto.ResponseBienesDTO;
import csjar.controlpatrimonial.entity.Bien;

public interface BienService {

	Bien obtenerEntidad(String codigo);
	ResponseBienesDTO obtenerBien(String codigo, Integer idEmpleado, String tipoActa);
	List<ResponseBienesDTO> obtenerBienes(Integer idAdquisicion);
	void generarBienes(RequestBienesDTO requestBienesnDTO);
	byte[] generarEtiquetas(List<RequestEtiquetaDTO> requestEtiquetaDTO) throws DocumentException, IOException, WriterException ;
	
}
