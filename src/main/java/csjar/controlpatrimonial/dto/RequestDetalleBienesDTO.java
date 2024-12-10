package csjar.controlpatrimonial.dto;

import lombok.Data;

@Data
public class RequestDetalleBienesDTO {

	private String descripcion;
	private Integer idModelo;
	private String serie;
	private String color;
	private String observacion;
	
}
