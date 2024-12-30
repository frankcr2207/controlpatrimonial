package csjar.controlpatrimonial.dto;

import java.util.List;

import lombok.Data;

@Data
public class RequestActaDTO {

	private Integer idEmpleado;
	private String correo;
	private String tipo;
	private Integer idSede;
	private Integer idArea;
	private List<RequestDetalleBienesDTO> bienes;
	
}
