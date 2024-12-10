package csjar.controlpatrimonial.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class RequestAdquisicionDTO {

	private Integer id;
	private String documento;
	private LocalDateTime fecAdquisicion;
	private LocalDateTime fecRegistro;
	private String estado;
	private Integer idTipoAdquisicion;
	private List<RequestDetalleAdquisicionDTO> detalle;
	
}
