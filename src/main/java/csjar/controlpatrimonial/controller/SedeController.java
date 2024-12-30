package csjar.controlpatrimonial.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import csjar.controlpatrimonial.dto.ResponseSedeDTO;
import csjar.controlpatrimonial.service.SedeService;

@RestController
@RequestMapping("/sede")
public class SedeController {

	private SedeService service;
	
	public SedeController(SedeService service) {
		super();
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<ResponseSedeDTO>> getSedes() {
		return new ResponseEntity<>(service.obtenerSedes(), HttpStatus.OK);
	}
	
}
