package csjar.controlpatrimonial.mapper.service;

import csjar.controlpatrimonial.domain.Usuario;
import csjar.controlpatrimonial.dto.ResponseUsuarioDTO;

public interface UsuarioMapperService {

	ResponseUsuarioDTO toDTO(Usuario usuario);
	Usuario toEntity(ResponseUsuarioDTO responseUsuarioDTO);
	
}
