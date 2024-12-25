package csjar.controlpatrimonial.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import csjar.controlpatrimonial.dto.ResponseClaseDTO;
import csjar.controlpatrimonial.dto.ResponseGrupoDTO;
import csjar.controlpatrimonial.service.GrupoClaseService;

@RestController
@RequestMapping("/grupoClase")
public class GrupoClaseController {
	
	private GrupoClaseService grupoClaseService;
	
	public GrupoClaseController(GrupoClaseService grupoClaseService) {
		super();
		this.grupoClaseService = grupoClaseService;
	}

	@GetMapping("/grupos")
	public ResponseEntity<List<ResponseGrupoDTO>> getGrupos() {
		return new ResponseEntity<>(grupoClaseService.listarGrupos(), HttpStatus.OK);
	}
	
	@GetMapping("/clases")
	public ResponseEntity<List<ResponseClaseDTO>> getClases() {
		return new ResponseEntity<>(grupoClaseService.listarClases(), HttpStatus.OK);
	}
	
}
