package csjar.controlpatrimonial.dto;

import lombok.Data;

@Data
public class ResponseUsuarioDTO {

	private Integer id;
	private String dni;
	private String nombres;
	private String apellidos;
	private String correo;
	
}
