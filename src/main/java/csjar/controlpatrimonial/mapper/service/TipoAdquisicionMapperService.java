package csjar.controlpatrimonial.mapper.service;

import java.util.List;

import csjar.controlpatrimonial.dto.ResponseTipoAdquisicionDTO;
import csjar.controlpatrimonial.entity.TipoAdquisicion;

public interface TipoAdquisicionMapperService {

	ResponseTipoAdquisicionDTO toDTO(TipoAdquisicion tipoAdquisicion);
	List<ResponseTipoAdquisicionDTO> toDTO(List<TipoAdquisicion> tipoAdquisicion);
	
}
