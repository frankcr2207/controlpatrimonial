package csjar.controlpatrimonial.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import csjar.controlpatrimonial.dto.RequestBienesDTO;
import csjar.controlpatrimonial.service.BienService;

@RequestMapping("/bien")
@RestController
public class BienController {

	private BienService bienService;
	
	public BienController(BienService bienService) {
		super();
		this.bienService = bienService;
	}
	
	@PostMapping("/generar")
	public ResponseEntity<ResponseEntity<HttpStatus>> generarBienes(@RequestBody RequestBienesDTO request) throws NoSuchAlgorithmException {
		this.bienService.generarBienes(request);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
