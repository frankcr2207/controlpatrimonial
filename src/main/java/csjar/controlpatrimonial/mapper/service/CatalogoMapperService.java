package csjar.controlpatrimonial.mapper.service;

import java.util.List;

import csjar.controlpatrimonial.domain.Catalogo;
import csjar.controlpatrimonial.dto.ResponseCatalogoDTO;

public interface CatalogoMapperService {

	ResponseCatalogoDTO toDTO(Catalogo catalogo);
	List<ResponseCatalogoDTO> toDTO(List<Catalogo> catalogo);
	
}
