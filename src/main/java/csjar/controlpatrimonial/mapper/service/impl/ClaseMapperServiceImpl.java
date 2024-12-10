package csjar.controlpatrimonial.mapper.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import csjar.controlpatrimonial.domain.Clase;
import csjar.controlpatrimonial.dto.ResponseClaseDTO;
import csjar.controlpatrimonial.mapper.service.ClaseMapperService;

@Service
public class ClaseMapperServiceImpl implements ClaseMapperService {

	private static final ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public List<ResponseClaseDTO> toDTO(List<Clase> clases) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper.map(clases, new TypeToken<List<ResponseClaseDTO>>(){}.getType());
	}

}
