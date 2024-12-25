package csjar.controlpatrimonial.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import csjar.controlpatrimonial.dto.ResponseTipoAdquisicionDTO;
import csjar.controlpatrimonial.service.TipoAdquisicionService;

@RestController
@RequestMapping("/tipoAdquisicion")
public class TipoAdquisicionController {

	private TipoAdquisicionService service;
	
	public TipoAdquisicionController(TipoAdquisicionService service) {
		super();
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<ResponseTipoAdquisicionDTO>> getTipoAdquisicion() {
		return new ResponseEntity<>(service.listarTiposAdquisicion(), HttpStatus.OK);
	}
	
}
