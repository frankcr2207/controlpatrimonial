package csjar.controlpatrimonial.service;

import java.io.IOException;
import java.util.List;

import com.google.zxing.WriterException;
import com.itextpdf.text.DocumentException;

import csjar.controlpatrimonial.dto.RequestBienesDTO;
import csjar.controlpatrimonial.dto.RequestEtiquetaDTO;
import csjar.controlpatrimonial.dto.ResponseBienesDTO;

public interface BienService {

	ResponseBienesDTO obtenerBien(String codigo);
	List<ResponseBienesDTO> obtenerBienes(Integer idAdquisicion);
	void generarBienes(RequestBienesDTO requestBienesnDTO);
	byte[] generarEtiquetas(List<RequestEtiquetaDTO> requestEtiquetaDTO) throws DocumentException, IOException, WriterException ;
	
}
