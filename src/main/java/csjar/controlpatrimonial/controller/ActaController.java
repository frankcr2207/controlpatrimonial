package csjar.controlpatrimonial.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import csjar.controlpatrimonial.dto.RequestActaDTO;
import csjar.controlpatrimonial.service.ActaService;

@RequestMapping("/acta")
@RestController
public class ActaController {

	private ActaService actaService;
	
	public ActaController(ActaService actaService) {
		super();
		this.actaService = actaService;
	}
	
	@PostMapping
	public ResponseEntity<ResponseEntity<HttpStatus>> gestionarMovimiento(@RequestBody RequestActaDTO requestActaDTO) throws Exception {
		this.actaService.guardarActa(requestActaDTO);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	 
}
