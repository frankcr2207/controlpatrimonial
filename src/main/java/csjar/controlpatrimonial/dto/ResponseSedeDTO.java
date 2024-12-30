package csjar.controlpatrimonial.dto;

import java.util.List;

import lombok.Data;

@Data
public class ResponseSedeDTO {

	private Integer id;
	private String codigo;
	private String denominacion;
	private String direccion;
	private List<ResponseAreaDTO> areas;
	
}
