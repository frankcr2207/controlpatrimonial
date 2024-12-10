package csjar.controlpatrimonial.service;

import java.util.List;

import csjar.controlpatrimonial.domain.Catalogo;

public interface CatalogoService {

	List<Catalogo> obtenerCatalogo(List<Integer> ids);
	
}
