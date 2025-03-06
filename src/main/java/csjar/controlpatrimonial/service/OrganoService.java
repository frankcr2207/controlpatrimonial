package csjar.controlpatrimonial.service;

import java.util.List;

import csjar.controlpatrimonial.dto.ResponseOrganoDTO;

public interface OrganoService {

	List<ResponseOrganoDTO> listarOrganos(Integer idSede);
	
}
