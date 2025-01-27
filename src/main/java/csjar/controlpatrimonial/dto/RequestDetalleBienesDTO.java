package csjar.controlpatrimonial.dto;

import lombok.Data;

@Data
public class RequestDetalleBienesDTO {

	private Integer idCatalogo;
	private String descripcion;
	private Integer idModelo;
	private String serie;
	private String color;
	private String observacion;
	
	private String codigoPatrimonial;
	private String estadoConservacion;
	private Integer id;
	private Integer idAdquisicion;
	
}
