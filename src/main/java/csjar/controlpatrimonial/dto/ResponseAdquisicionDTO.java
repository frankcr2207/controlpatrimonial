package csjar.controlpatrimonial.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ResponseAdquisicionDTO {

	private Integer id;
	private String documento;
	private String estado;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime fecAdquisicion;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime fecRegistro;
	private List<ResponseDetalleAdquisicionDTO> detalleAdquisicion;
	
}