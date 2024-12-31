package csjar.controlpatrimonial.dto;

import lombok.Data;

@Data
public class ResponseBienesDTO {

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
	
}
