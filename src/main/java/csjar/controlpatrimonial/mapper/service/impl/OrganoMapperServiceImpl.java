package csjar.controlpatrimonial.mapper.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import csjar.controlpatrimonial.dto.ResponseOrganoDTO;
import csjar.controlpatrimonial.entity.Organo;
import csjar.controlpatrimonial.mapper.service.OrganoMapperService;

@Service
public class OrganoMapperServiceImpl implements OrganoMapperService {

	private static final ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public List<ResponseOrganoDTO> toDTO(List<Organo> organos) {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper.map(organos, new TypeToken<List<ResponseOrganoDTO>>(){}.getType());
	}

}
