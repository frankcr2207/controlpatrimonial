package csjar.controlpatrimonial.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import csjar.controlpatrimonial.dto.ResponseMarcaDTO;
import csjar.controlpatrimonial.service.MarcaService;

@RequestMapping("/marca")
@RestController
public class MarcaController {

	private MarcaService marcaService;
	
	public MarcaController(MarcaService marcaService) {
		super();
		this.marcaService = marcaService;
	}

	@GetMapping
	public ResponseEntity<List<ResponseMarcaDTO>> listarMarcas() throws NoSuchAlgorithmException {
		return new ResponseEntity<>(marcaService.listarMarcas(), HttpStatus.OK);
	}
}
