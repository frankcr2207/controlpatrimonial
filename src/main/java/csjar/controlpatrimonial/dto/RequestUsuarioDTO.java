package csjar.controlpatrimonial.dto;

import lombok.Data;

@Data
public class RequestUsuarioDTO {

	private Integer id;
	private String login;
	private String clave;
	private String dni;
	private String nombres;
	private String apellidos;
	private String correo;
	private String estado;
	private Integer idPerfil;
	
}
