package csjar.controlpatrimonial.mapper.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import csjar.controlpatrimonial.dto.ResponseSedeDTO;
import csjar.controlpatrimonial.entity.Sede;
import csjar.controlpatrimonial.mapper.service.SedeMapperService;

@Service
public class SedeMapperServiceImpl implements SedeMapperService {

	private static final ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public List<ResponseSedeDTO> toDTO(List<Sede> sedes) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper.map(sedes, new TypeToken<List<ResponseSedeDTO>>(){}.getType());
	}

}
