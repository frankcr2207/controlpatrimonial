package csjar.controlpatrimonial.mapper.service;

import java.util.List;

import csjar.controlpatrimonial.domain.Grupo;
import csjar.controlpatrimonial.dto.ResponseGrupoDTO;

public interface GrupoMapperService {

	List<ResponseGrupoDTO> toDTO(List<Grupo> grupos);
	
}
