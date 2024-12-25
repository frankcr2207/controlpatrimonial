package csjar.controlpatrimonial.mapper.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import csjar.controlpatrimonial.domain.Catalogo;
import csjar.controlpatrimonial.dto.ResponseCatalogoDTO;
import csjar.controlpatrimonial.mapper.service.CatalogoMapperService;

@Service
public class CatalogoMapperServiceImpl implements CatalogoMapperService {

	private static final ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public ResponseCatalogoDTO toDTO(Catalogo catalogo) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper.map(catalogo, ResponseCatalogoDTO.class);
	}

	@Override
	public List<ResponseCatalogoDTO> toDTO(List<Catalogo> catalogo) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper.map(catalogo, new TypeToken<List<ResponseCatalogoDTO>>(){}.getType());
	}

}
