package csjar.controlpatrimonial.mapper.service;

import java.util.List;

import csjar.controlpatrimonial.dto.RequestAdquisicionDTO;
import csjar.controlpatrimonial.dto.ResponseAdquisicionDTO;
import csjar.controlpatrimonial.entity.Adquisicion;

public interface AdquisicionMapperService {

	Adquisicion toEntity(RequestAdquisicionDTO requestAdquisicionDTO);
	List<ResponseAdquisicionDTO> toDTO(List<Adquisicion> adquisicion);
	ResponseAdquisicionDTO toDTO(Adquisicion adquisicion);
	
}
