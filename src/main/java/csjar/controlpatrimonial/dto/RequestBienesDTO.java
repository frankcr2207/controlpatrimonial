package csjar.controlpatrimonial.dto;

import java.util.List;

import lombok.Data;

@Data
public class RequestBienesDTO {

	private Integer idAdquisicion;
	private List<RequestDetalleBienesDTO> bienes;
	
}
