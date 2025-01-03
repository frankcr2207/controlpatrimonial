package csjar.controlpatrimonial.mapper.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import csjar.controlpatrimonial.dto.ResponseTipoAdquisicionDTO;
import csjar.controlpatrimonial.entity.TipoAdquisicion;
import csjar.controlpatrimonial.mapper.service.TipoAdquisicionMapperService;

@Service
public class TipoAdquisicionMapperServiceImpl implements TipoAdquisicionMapperService{

	private static final ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public ResponseTipoAdquisicionDTO toDTO(TipoAdquisicion tipoAdquisicion) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper.map(tipoAdquisicion, ResponseTipoAdquisicionDTO.class);
	}

	@Override
	public List<ResponseTipoAdquisicionDTO> toDTO(List<TipoAdquisicion> tipoAdquisicion) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper.map(tipoAdquisicion, new TypeToken<List<ResponseTipoAdquisicionDTO>>(){}.getType());
	}
	
}
