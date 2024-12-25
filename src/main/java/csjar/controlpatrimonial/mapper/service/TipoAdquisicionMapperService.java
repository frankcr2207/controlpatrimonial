package csjar.controlpatrimonial.mapper.service;

import java.util.List;

import csjar.controlpatrimonial.domain.TipoAdquisicion;
import csjar.controlpatrimonial.dto.ResponseTipoAdquisicionDTO;

public interface TipoAdquisicionMapperService {

	ResponseTipoAdquisicionDTO toDTO(TipoAdquisicion tipoAdquisicion);
	List<ResponseTipoAdquisicionDTO> toDTO(List<TipoAdquisicion> tipoAdquisicion);
	
}
