package csjar.controlpatrimonial.mapper.service;

import java.util.List;

import csjar.controlpatrimonial.dto.ResponseCatalogoDTO;
import csjar.controlpatrimonial.entity.Catalogo;

public interface CatalogoMapperService {

	ResponseCatalogoDTO toDTO(Catalogo catalogo);
	List<ResponseCatalogoDTO> toDTO(List<Catalogo> catalogo);
	
}
