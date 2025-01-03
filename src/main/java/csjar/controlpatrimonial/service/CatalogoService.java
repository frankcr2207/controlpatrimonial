package csjar.controlpatrimonial.service;

import java.util.List;

import csjar.controlpatrimonial.dto.ResponseCatalogoDTO;
import csjar.controlpatrimonial.entity.Catalogo;

public interface CatalogoService {

	List<Catalogo> obtenerCatalogo(List<Integer> ids);
	List<ResponseCatalogoDTO> obtenerCatalogo(Integer idGrupo, Integer idClase);
	
}
