package csjar.controlpatrimonial.mapper.service;

import java.util.List;

import csjar.controlpatrimonial.domain.Adquisicion;
import csjar.controlpatrimonial.dto.RequestAdquisicionDTO;
import csjar.controlpatrimonial.dto.ResponseAdquisicionDTO;

public interface AdquisicionMapperService {

	Adquisicion toEntity(RequestAdquisicionDTO requestAdquisicionDTO);
	RequestAdquisicionDTO toEntity(Adquisicion adquisicion);
	List<ResponseAdquisicionDTO> toDTO(List<Adquisicion> adquisicion);
	
}
