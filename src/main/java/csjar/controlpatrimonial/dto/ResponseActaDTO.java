package csjar.controlpatrimonial.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ResponseActaDTO {

	private String dni;
	private String nombresApellidos;
	private LocalDateTime fecha;
	private String sede;
	private String direccion;
	
	private Integer idActa;
	private String status;
	private String mensaje;
	private String numeroActa;
	private String estado;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime fecRegistro;
	
}
