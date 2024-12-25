package csjar.controlpatrimonial.service;

import java.util.List;

import csjar.controlpatrimonial.domain.GrupoClase;
import csjar.controlpatrimonial.dto.ResponseClaseDTO;
import csjar.controlpatrimonial.dto.ResponseGrupoDTO;

public interface GrupoClaseService {
	
	GrupoClase obtenerEntidad(Integer idGrupo, Integer idClase);
	List<ResponseGrupoDTO> listarGrupos();
	List<ResponseClaseDTO> listarClases();

}
