package csjar.controlpatrimonial.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ResponseBienDTO {

	private Integer orden;
	private String denominacion;
	private String marca;
	private String modelo;
	private String color;
	private String serie;
	private String estado;
	private String observaciones;
	
	private String catalogo;
	private String codigoPatrimonial;
	private String descripcion;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime fecRegistro;
	private String empleado;
	private Integer idActa;
	private String conservacion;
	
}
