package csjar.controlpatrimonial.mapper.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import csjar.controlpatrimonial.domain.Adquisicion;
import csjar.controlpatrimonial.dto.RequestAdquisicionDTO;
import csjar.controlpatrimonial.dto.ResponseAdquisicionDTO;
import csjar.controlpatrimonial.mapper.service.AdquisicionMapperService;

@Service
public class AdquisicionMapperServiceImpl implements AdquisicionMapperService {

	private static final ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public Adquisicion toEntity(RequestAdquisicionDTO requestAdquisicionDTO) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper.map(requestAdquisicionDTO, Adquisicion.class);
	}

	@Override
	public List<ResponseAdquisicionDTO> toDTO(List<Adquisicion> adquisicion) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper.map(adquisicion, new TypeToken<List<ResponseAdquisicionDTO>>(){}.getType());
	}

	@Override
	public ResponseAdquisicionDTO toDTO(Adquisicion adquisicion) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper.map(adquisicion, ResponseAdquisicionDTO.class);
	}

}
