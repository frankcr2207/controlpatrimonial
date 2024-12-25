package csjar.controlpatrimonial.mapper.service;

import java.util.List;

import csjar.controlpatrimonial.domain.Marca;
import csjar.controlpatrimonial.dto.ResponseMarcaDTO;

public interface MarcaMapperService {

	ResponseMarcaDTO toDTO(Marca marca);
	List<ResponseMarcaDTO> toDTO(List<Marca> marcas);
	
}
