package csjar.controlpatrimonial.service;

import java.util.List;

import csjar.controlpatrimonial.dto.ResponseClaseDTO;
import csjar.controlpatrimonial.dto.ResponseGrupoDTO;
import csjar.controlpatrimonial.entity.GrupoClase;

public interface GrupoClaseService {
	
	GrupoClase obtenerEntidad(Integer idGrupo, Integer idClase);
	List<ResponseGrupoDTO> listarGrupos();
	List<ResponseClaseDTO> listarClases();

}
