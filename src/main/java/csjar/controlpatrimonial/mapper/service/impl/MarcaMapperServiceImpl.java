package csjar.controlpatrimonial.mapper.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import csjar.controlpatrimonial.dto.ResponseMarcaDTO;
import csjar.controlpatrimonial.entity.Marca;
import csjar.controlpatrimonial.mapper.service.MarcaMapperService;

@Service
public class MarcaMapperServiceImpl implements MarcaMapperService {

	private static final ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public ResponseMarcaDTO toDTO(Marca marca) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper.map(marca, ResponseMarcaDTO.class);
	}

	@Override
	public List<ResponseMarcaDTO> toDTO(List<Marca> marcas) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper.map(marcas, new TypeToken<List<ResponseMarcaDTO>>(){}.getType());
	}

}
