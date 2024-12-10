package csjar.controlpatrimonial.mapper.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import csjar.controlpatrimonial.domain.Perfil;
import csjar.controlpatrimonial.dto.RequestPerfilDTO;
import csjar.controlpatrimonial.dto.ResponsePerfilDTO;
import csjar.controlpatrimonial.mapper.service.PerfilMapperService;

@Service
public class PerfilMapperServiceImpl implements PerfilMapperService {

	private static final ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public ResponsePerfilDTO toDTO(Perfil perfil) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper.map(perfil, ResponsePerfilDTO.class);
	}

	@Override
	public List<ResponsePerfilDTO> toDTO(List<Perfil> perfiles) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper.map(perfiles, new TypeToken<List<ResponsePerfilDTO>>(){}.getType());
	}

	@Override
	public Perfil toEntity(RequestPerfilDTO perfilDTO) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper.map(perfilDTO, Perfil.class);
	}

	@Override
	public List<Perfil> toEntity(List<RequestPerfilDTO> perfilDto) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper.map(perfilDto, new TypeToken<List<Perfil>>(){}.getType());
	}

}
