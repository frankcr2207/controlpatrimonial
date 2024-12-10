package csjar.controlpatrimonial.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class ResponseAdquisicionDTO {

	private Integer id;
	private String documento;
	private String estado;
	private LocalDateTime fecAdquisicion;
	private LocalDateTime fecRegistro;
	private List<ResponseDetalleAdquisicionDTO> detalleAdquisicion;
	
}