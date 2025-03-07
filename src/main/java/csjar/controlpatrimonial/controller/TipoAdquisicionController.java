package csjar.controlpatrimonial.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import csjar.controlpatrimonial.dto.RequestTipoAdquisicionDTO;
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
	
	@PostMapping
	public ResponseEntity<HttpStatus> generarTipoAdquisicion(@RequestBody RequestTipoAdquisicionDTO request) {
		this.service.guardarAdquisicion(request);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping
	public ResponseEntity<HttpStatus> modificarTipoAdquisicion(@RequestBody RequestTipoAdquisicionDTO request) {
		this.service.guardarAdquisicion(request);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping
	public ResponseEntity<List<ResponseTipoAdquisicionDTO>> getTipoAdquisicion() {
		return new ResponseEntity<>(service.listarTiposAdquisicion(), HttpStatus.OK);
	}
	
}
