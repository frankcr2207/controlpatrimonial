package csjar.controlpatrimonial.service;

import java.util.List;

import csjar.controlpatrimonial.domain.Catalogo;
import csjar.controlpatrimonial.dto.ResponseCatalogoDTO;

public interface CatalogoService {

	List<Catalogo> obtenerCatalogo(List<Integer> ids);
	List<ResponseCatalogoDTO> obtenerCatalogo(Integer idGrupo, Integer idClase);
	
}
