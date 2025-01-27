package csjar.controlpatrimonial.dto;

import java.util.List;

import lombok.Data;

@Data
public class RequestAdquisicionDTO {

	private Integer id;
	private String documento;
	private String fecAdquisicion;
	private Integer idTipoAdquisicion;
	private String regularizar;
	private List<RequestDetalleAdquisicionDTO> detalle;
	
}
