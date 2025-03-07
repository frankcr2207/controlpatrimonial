package csjar.controlpatrimonial.service;

import java.util.List;

import csjar.controlpatrimonial.dto.ResponseOrganoDTO;
import csjar.controlpatrimonial.entity.SedeOrgano;

public interface OrganoService {

	List<ResponseOrganoDTO> listarOrganos(Integer idSede);
	
	List<SedeOrgano> obtenerEntidades(Integer idSede, List<Integer> idsOrgano);
	
}
