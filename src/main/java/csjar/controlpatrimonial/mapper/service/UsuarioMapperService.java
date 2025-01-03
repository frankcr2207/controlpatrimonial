package csjar.controlpatrimonial.mapper.service;

import java.util.List;

import csjar.controlpatrimonial.dto.ResponseUsuarioDTO;
import csjar.controlpatrimonial.entity.Usuario;

public interface UsuarioMapperService {

	ResponseUsuarioDTO toDTO(Usuario usuario);
	List<ResponseUsuarioDTO> toDTO(List<Usuario> usuario);
	Usuario toEntity(ResponseUsuarioDTO responseUsuarioDTO);
	
}
