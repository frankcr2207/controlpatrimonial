package csjar.controlpatrimonial.mapper.service;

import java.util.List;

import csjar.controlpatrimonial.dto.ResponseOrganoDTO;
import csjar.controlpatrimonial.entity.Organo;

public interface OrganoMapperService {

	List<ResponseOrganoDTO> toDTO(List<Organo> organos);
	
}
