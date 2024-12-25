package csjar.controlpatrimonial.dto;

import java.util.List;

import lombok.Data;

@Data
public class ResponseMarcaDTO {

	private Integer id;
	private String nombre;
	private List<ResponseModeloDTO> modelos;
	
}
