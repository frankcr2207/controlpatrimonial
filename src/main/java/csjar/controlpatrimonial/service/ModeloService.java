package csjar.controlpatrimonial.service;

import java.util.List;

import csjar.controlpatrimonial.entity.Modelo;

public interface ModeloService {

	List<Modelo> obtenerEntidades(List<Integer> ids);
	
}
