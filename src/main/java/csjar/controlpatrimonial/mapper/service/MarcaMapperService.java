package csjar.controlpatrimonial.mapper.service;

import java.util.List;

import csjar.controlpatrimonial.dto.ResponseMarcaDTO;
import csjar.controlpatrimonial.entity.Marca;

public interface MarcaMapperService {

	ResponseMarcaDTO toDTO(Marca marca);
	List<ResponseMarcaDTO> toDTO(List<Marca> marcas);
	
}
