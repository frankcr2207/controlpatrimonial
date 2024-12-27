package csjar.controlpatrimonial.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import csjar.controlpatrimonial.dto.RequestAdquisicionDTO;
import csjar.controlpatrimonial.dto.ResponseAdquisicionDTO;
import csjar.controlpatrimonial.service.AdquisicionService;

@RequestMapping("/adquisicion")
@RestController
public class AdquisicionController {
	
	private AdquisicionService adquisicionService;
	
	public AdquisicionController(AdquisicionService adquisicionService) {
		super();
		this.adquisicionService = adquisicionService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseAdquisicionDTO> verAdquisicion(@PathVariable Integer id) throws NoSuchAlgorithmException {
		return new ResponseEntity<>(adquisicionService.verAdquisicion(id), HttpStatus.OK);
	}
	
	@GetMapping("/buscar/{documento}")
	public ResponseEntity<List<ResponseAdquisicionDTO>> buscarAdquisicion(@PathVariable String documento) throws NoSuchAlgorithmException {
		return new ResponseEntity<>(adquisicionService.buscarAdquisicion(documento), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<ResponseEntity<HttpStatus>> gestionarAdquisicion(@RequestBody RequestAdquisicionDTO request) throws NoSuchAlgorithmException {
		this.adquisicionService.guardarAdquisicion(request);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
