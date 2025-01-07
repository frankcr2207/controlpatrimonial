package csjar.controlpatrimonial.service;

import java.io.IOException;
import java.util.List;

import com.google.zxing.WriterException;
import com.itextpdf.text.DocumentException;

import csjar.controlpatrimonial.dto.RequestBienesDTO;
import csjar.controlpatrimonial.dto.RequestEtiquetaDTO;
import csjar.controlpatrimonial.dto.ResponseBienDTO;
import csjar.controlpatrimonial.dto.ResponseTrazabilidadDTO;
import csjar.controlpatrimonial.entity.Bien;

public interface BienService {

	Bien obtenerEntidad(String codigo);
	ResponseBienDTO obtenerBien(String codigo, Integer idEmpleado, String tipoActa);
	List<ResponseBienDTO> obtenerBienes(Integer idAdquisicion);
	void generarBienes(RequestBienesDTO requestBienesnDTO);
	byte[] generarEtiquetas(List<RequestEtiquetaDTO> requestEtiquetaDTO) throws DocumentException, IOException, WriterException ;
	ResponseTrazabilidadDTO obtenerTrazabilidad(String codigo);
	
}
