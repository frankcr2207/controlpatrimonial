package csjar.controlpatrimonial.mapper.service;

import java.util.List;

import csjar.controlpatrimonial.dto.ResponseGrupoDTO;
import csjar.controlpatrimonial.entity.Grupo;

public interface GrupoMapperService {

	List<ResponseGrupoDTO> toDTO(List<Grupo> grupos);
	
}
