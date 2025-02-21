package csjar.controlpatrimonial.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RequestDetalleAdquisicionDTO {

	private Integer idCatalogo;
	private Integer cantidad;
	private BigDecimal costoUnitario;
	
}
