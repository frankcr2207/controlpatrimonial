package csjar.controlpatrimonial.dto;

import lombok.Data;

@Data
public class ResponseDetalleAdquisicionDTO {

	private Integer id;
	private ResponseCatalogoDTO catalogo;
	private Integer cantidad;
	
}
