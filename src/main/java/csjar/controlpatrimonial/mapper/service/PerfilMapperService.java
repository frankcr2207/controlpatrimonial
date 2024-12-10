package csjar.controlpatrimonial.mapper.service;

import java.util.List;

import csjar.controlpatrimonial.domain.Perfil;
import csjar.controlpatrimonial.dto.RequestPerfilDTO;
import csjar.controlpatrimonial.dto.ResponsePerfilDTO;

public interface PerfilMapperService {

	ResponsePerfilDTO toDTO(Perfil perfil);
	List<ResponsePerfilDTO> toDTO(List<Perfil> perfiles);
	Perfil toEntity(RequestPerfilDTO perfilDTO);
	List<Perfil> toEntity(List<RequestPerfilDTO> perfilDto);
	
}
