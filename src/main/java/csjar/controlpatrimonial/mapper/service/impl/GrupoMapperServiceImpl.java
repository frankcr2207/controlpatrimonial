package csjar.controlpatrimonial.mapper.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import csjar.controlpatrimonial.domain.Grupo;
import csjar.controlpatrimonial.dto.ResponseGrupoDTO;
import csjar.controlpatrimonial.dto.ResponsePerfilDTO;
import csjar.controlpatrimonial.mapper.service.GrupoMapperService;

@Service
public class GrupoMapperServiceImpl implements GrupoMapperService {

	private static final ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public List<ResponseGrupoDTO> toDTO(List<Grupo> grupos) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper.map(grupos, new TypeToken<List<ResponsePerfilDTO>>(){}.getType());
	}

}
