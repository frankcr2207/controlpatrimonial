package csjar.controlpatrimonial.mapper.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import csjar.controlpatrimonial.dto.ResponseBienDTO;
import csjar.controlpatrimonial.entity.Bien;
import csjar.controlpatrimonial.mapper.service.BienMapperService;

@Service
public class BienMapperServiceImpl implements BienMapperService {

	private static final ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public ResponseBienDTO toDTO(Bien bien) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper.map(bien, ResponseBienDTO.class);
	}

}
