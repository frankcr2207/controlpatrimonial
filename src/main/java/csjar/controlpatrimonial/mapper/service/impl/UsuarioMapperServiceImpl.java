package csjar.controlpatrimonial.mapper.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import csjar.controlpatrimonial.dto.ResponseUsuarioDTO;
import csjar.controlpatrimonial.entity.Usuario;
import csjar.controlpatrimonial.mapper.service.UsuarioMapperService;

@Service
public class UsuarioMapperServiceImpl implements UsuarioMapperService{

	private static final ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public ResponseUsuarioDTO toDTO(Usuario usuario) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper.map(usuario, ResponseUsuarioDTO.class);
	}
	
	@Override
	public Usuario toEntity(ResponseUsuarioDTO responseUsuarioDTO) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper.map(responseUsuarioDTO, Usuario.class);
	}

	@Override
	public List<ResponseUsuarioDTO> toDTO(List<Usuario> usuario) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper.map(usuario, new TypeToken<List<ResponseUsuarioDTO>>(){}.getType());
	}
	
}
