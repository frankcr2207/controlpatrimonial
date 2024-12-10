package csjar.controlpatrimonial.dto;

import java.util.List;

import lombok.Data;

@Data
public class RequestBienesDTO {

	private Integer idDetalleAdquisicion;
	private List<RequestDetalleBienesDTO> bienes;
	
}
