package csjar.controlpatrimonial.dto;

import java.util.List;

import lombok.Data;

@Data
public class ResponseTrazabilidadDTO {

	private Integer id;
	private String codigo;
	private String catalogo;
	private String descripcion;
	private String conservacionActual;
	private String estadoActual;
	private String empleadoActual;
	private String ubicacionActual;
	private List<ResponseBienDTO> detalle;
	
}
