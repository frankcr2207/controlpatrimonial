package csjar.controlpatrimonial.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ResponseActaDTO {

	private String dni;
	private String nombresApellidos;
	private LocalDateTime fecha;
	private String sede;
	private String direccion;
	
}
