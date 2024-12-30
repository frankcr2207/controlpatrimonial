package csjar.controlpatrimonial.dto;

import lombok.Data;

@Data
public class RequestEmailDTO {

	private String remitente;
	private String destino;
	private String asunto;
	private String mensaje;
	private String archivo;
	
}
